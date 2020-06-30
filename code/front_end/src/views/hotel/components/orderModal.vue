<template>
    <a-modal
        :visible="orderModalVisible"
        title="预定详情"
        cancelText="取消"
        okText="下单"
        @cancel="cancelOrder"
        @ok="handleSubmit"
    >
        <a-form :form="form">
            <a-form-item v-bind="formItemLayout" label="房型信息">
                <span>{{ currentOrderRoom.roomType }}</span>
            </a-form-item>
            <a-form-item v-bind="formItemLayout" label="账户类型">
                <span>{{this.getVipType()}}</span>
            </a-form-item>
            <a-form-item v-bind="formItemLayout" label="入住人姓名">
                <a-input
                    v-decorator="[
                        'clientName',
                        { rules: [{required: true, message: '请填写入住人姓名', }] }
                    ]"
                />
            </a-form-item>
            <a-form-item v-bind="formItemLayout" label="手机号">
                <a-input
                    v-decorator="[
                        'phoneNumber',
                        { rules: [{required: true, message: '请填写入住人联系手机', }] }
                    ]"
                />
            </a-form-item>
            
            <a-form-item v-bind="formItemLayout" label="入住日期">
                <a-range-picker
                    :disabled-date="disabledDate"
                    format="YYYY-MM-DD"
                    @change="changeDate"
                    v-decorator="[
                        'date', 
                        {
                            rules: [{ required: true, message: '请选择入住时间' }]   
                        }
                    ]"
                    :placeholder="['入住日期','退房日期']"
                />
            </a-form-item>
            <a-form-item v-bind="formItemLayout" label="入住人数">
                <a-select
                    v-decorator="[
                        'peopleNum',
                        { rules: [{ required: true, message: '请选择入住人数' }] },
                    ]"
                    placeholder="请选择入住人数"
                    @change="changePeopleNum"
                >
                    <a-select-option :value="1">
                    1
                    </a-select-option>
                    <a-select-option :value="2">
                    2
                    </a-select-option>
                     <a-select-option :value="3">
                    3
                    </a-select-option>
                    <a-select-option :value="4">
                    4
                    </a-select-option>
                </a-select>
            </a-form-item>
            <a-form-item v-bind="formItemLayout" label="有无儿童">
                <a-radio-group
                    v-decorator="[
                        'haveChild',
                        { rules: [{required: true, message: '请选择有无儿童入住', }] }
                    ]"
                >
                    <a-radio :value="1">有</a-radio>
                    <a-radio :value="0">无</a-radio>
                </a-radio-group>
            </a-form-item>
            <a-form-item v-bind="formItemLayout" label="房间数">
                <a-input-number
                        v-decorator="[
                            'roomNum',
                            { rules: [{ required: true, message: '请选择房间数' }], initialValue: 1 }
                        ]" :min="1" :max="10"
                        @change="changeRoomNum"
                />
                <span class="ant-form-text">
                间
                </span>
            </a-form-item>
            <a-form-item v-bind="formItemLayout" label="房间单价">
                <span>{{ currentOrderRoom.price }}</span>
            </a-form-item>
            <a-form-item v-bind="formItemLayout" label="总价">
                <span>￥{{ totalPrice }}</span>
            </a-form-item>
            <a-divider></a-divider>
            <h3 v-if="hotelCouponList.length>0&&this.vipInfo.vipType!==2">酒店优惠</h3>
            <a-radio-group v-model="hotelCouponId" @change="useHotelCoupon">
                <a-table
                        :columns="columns"
                        :dataSource="hotelCouponList"
                        :showHeader="false"
                        v-if="hotelCouponList.length>0&&this.vipInfo.vipType!==2"
                >
                    <a-radio
                        slot="id"
                        slot-scope="record"
                        :value="record"
                        v-bind:disabled = isHotelCouponNotUsable(record)
                        v-if= "refresh"
                    >
                    </a-radio>
                </a-table>
            </a-radio-group>
            <h3 v-if="websiteCouponList.length>0">会员优惠</h3>
            <a-radio-group v-model="webCouponId" @change="useWebCoupon">
                <a-table
                    :columns="columns"
                    :dataSource="websiteCouponList"
                    :showHeader="false"
                    v-if="websiteCouponList.length>0"
                >
                    <a-radio
                        slot="id"
                        slot-scope="record"
                        :value="record"
                        v-bind:disabled = isWebCouponNotUsable(record)
                        v-if= "refresh"
                    >
                    </a-radio>
                </a-table>
            </a-radio-group>
             <a-form-item v-bind="formItemLayout" label="结算后总价">
                <span>￥{{ finalPrice }}</span>
            </a-form-item>
        </a-form>
    </a-modal>
</template>
<script>
import { mapGetters, mapMutations, mapActions } from 'vuex'
const moment = require('moment')
const paginationProps = {
    disabled: true
}
const columns = [
    {  
        title: '勾选',
        dataIndex: 'id',
        scopedSlots: {customRender: 'id'},
        width:50
    },
    {
        title: '优惠名称',
        dataIndex: 'couponName',
        scopedSlots: {customRender: 'couponName'},
        width:200
    },
    {
        title: '优惠简介',
        dataIndex: 'description',
        width:200
    },
  ];
export default {
    name: 'orderModal',
    data() {
        return {
            formItemLayout: {
                labelCol: {
                    xs: { span: 12 },
                    sm: { span: 6 },
                },
                wrapperCol: {
                    xs: { span: 24 },
                    sm: { span: 16 },
                },
            },
            totalPrice: '',
            columns,
            hotelCouponId: 0,
            webCouponId:0,
            finalPrice: '',
            duration:'',
            roomNum:1,
            refresh:1,
        }
    },
    computed: {
        ...mapGetters([
            'orderModalVisible',
            'currentOrderRoom',
            'currentHotelId',
            'currentHotelInfo',
            'userId',
            'orderMatchCouponList',
            'hotelCouponList',
            'websiteCouponList',
            'vipInfo'
        ]),

    },
    mounted() {
        this.getHotelCouponList(Number(this.$route.params.hotelId));
        this.getWebsiteCoupons();
        this.getVipInfo();
    },
    beforeCreate() {
        this.form = this.$form.createForm(this, { name: 'orderModal' });
    },
    methods: {
        ...mapMutations([
            'set_orderModalVisible',
            'set_orderMatchCouponList'
        ]),
        ...mapActions([
            'addOrder',
            'getOrderMatchCoupons',
            'getHotelCouponList',
            'getWebsiteCoupons',
            'getVipInfo'
        ]),
        getVipType(){
            if (this.vipInfo.vipType == 0){
                return "普通用户"
            }
            if (this.vipInfo.vipType == 1){
                return "个人会员"
            }
            if (this.vipInfo.vipType == 2){
                return "企业会员（只能使用企业优惠）"
            }
        },
        cancelOrder() {
            this.set_orderModalVisible(false)
            this.set_orderMatchCouponList([])
            this.totalPrice = ''
            this.finalPrice = ''
            this.hotelCouponId = 0
            this.webCouponId = 0
            this.duration = ''
            this.roomNum = 0
            this.form.resetFields()
        },
        moment,
        disabledDate(current) {
            // Can not select days before today and today
            return current && current < moment().startOf('day');
        },
        changeDate(v) {
            this.duration = v;
            this.totalPrice = this.form.getFieldValue('roomNum') * moment(v[1]).diff(moment(v[0]), 'day') * Number(this.currentOrderRoom.price)
            this.finalPrice = this.totalPrice
        },
        changePeopleNum(v){
        },
        changeRoomNum(v) {
            this.roomNum =  Number(v)
            if(this.totalPrice != '') {
                this.totalPrice = Number(v) * Number(this.currentOrderRoom.price) * moment(this.form.getFieldValue('date')[1]).diff(moment(this.form.getFieldValue('date')[0]), 'day')
            }
            this.finalPrice = this.totalPrice
        },
        useHotelCoupon(){
            this.webCouponId = 0
            this.finalPrice = this.totalPrice
            if(this.hotelCouponId>0&&!this.isHotelCouponNotUsable(this.hotelCouponId)){
                let i = 0;
                for(i;i<this.orderMatchCouponList.length;i++){
                    if (this.orderMatchCouponList[i].id ===  this.hotelCouponId){
                        break
                    }
                }
                let item = this.orderMatchCouponList[i]
                if(item.discountMoney){this.finalPrice= this.finalPrice-item.discountMoney}
                else{this.finalPrice= this.finalPrice*item.discount}
                this.finalPrice = parseFloat(this.finalPrice).toFixed(2)
            }
            else {
                //在价钱变动的时候，可能优惠政策就不能使用了，但是以前选到过，所以要重置
                this.hotelCouponId = 0
            }
        },
        useWebCoupon(){
          this.hotelCouponId = 0;
          this.finalPrice = this.totalPrice;
          if (this.webCouponId>0){
              let i = 0;
              for(i;i<this.orderMatchCouponList.length;i++){
                  if (this.orderMatchCouponList[i].id ===  this.webCouponId){
                      break
                  }
              }
              let item = this.orderMatchCouponList[i];
              this.finalPrice= (this.finalPrice*item.discount).toFixed(2)
          }
        },
        isHotelCouponNotUsable(id_){
            let i = 0;
            for(i;i<this.orderMatchCouponList.length;i++){
                if (this.orderMatchCouponList[i].id ===  id_){
                    return false;
                }
            }
            return true
        },
        isWebCouponNotUsable(id_){
            let i = 0;
            for(i;i<this.orderMatchCouponList.length;i++){
                if (this.orderMatchCouponList[i].id === id_){
                    return false;
                }
            }
            return true
        },
        handleSubmit(e) {
            e.preventDefault();
            this.form.validateFieldsAndScroll((err, values) => {
                if (!err) {
                    const data = {
                        hotelId: this.currentHotelId,
                        hotelName: this.currentHotelInfo.hotelName,
                        userId: Number(this.userId),
                        roomId: this.currentOrderRoom.id,
                        checkInDate: moment(this.form.getFieldValue('date')[0]).format('YYYY-MM-DD'),
                        checkOutDate: moment(this.form.getFieldValue('date')[1]).format('YYYY-MM-DD'),
                        roomType: this.currentOrderRoom.roomType == '大床房' ? 'BigBed' : this.currentOrderRoom.roomType == '双床房' ? 'DoubleBed' : 'Family',
                        roomNum: this.form.getFieldValue('roomNum'),
                        peopleNum: this.form.getFieldValue('peopleNum'),
                        haveChild: this.form.getFieldValue('haveChild'),
                        createDate: '',
                        price: this.finalPrice
                    }
                    this.addOrder(data)
                }
            });
        },
    },
    watch:{
        //价钱变化导致可用优惠政策的变化
        roomNum(newValue,oldValue) {
            if (newValue !== 0) {
                let data = {
                    userId: Number(this.userId),
                    hotelId: this.currentHotelId,
                    orderPrice: this.totalPrice,
                    roomNum: this.form.getFieldValue('roomNum'),
                    checkIn: moment(this.form.getFieldValue('date')[0]).format('YYYY-MM-DD'),
                    checkOut: moment(this.form.getFieldValue('date')[1]).format('YYYY-MM-DD'),
                }
                this.getOrderMatchCoupons(data).then(res => {
                    //需要 验证变动后，之前选择的优惠政策能不能使用
                    this.useHotelCoupon()
                })
            }
        },
        duration(newValue,oldValue) {
            if (newValue !== '') {
                let data = {
                    userId: Number(this.userId),
                    hotelId: this.currentHotelId,
                    orderPrice: this.totalPrice,
                    roomNum: this.form.getFieldValue('roomNum'),
                    checkIn: moment(this.form.getFieldValue('date')[0]).format('YYYY-MM-DD'),
                    checkOut: moment(this.form.getFieldValue('date')[1]).format('YYYY-MM-DD'),
                }
                this.getOrderMatchCoupons(data).then(res => {
                    //需要 验证变动后，之前选择的优惠政策能不能使用
                    this.useHotelCoupon()
                    this.useWebCoupon()
                })
            }
        },
        },
    }
</script>