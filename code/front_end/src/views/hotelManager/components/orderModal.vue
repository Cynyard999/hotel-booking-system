<template>
    <a-modal
            :visible="orderModalVisible"
            title="酒店内部预订"
            cancelText="取消"
            okText="下单"
            @cancel="cancelOrder"
            @ok="handleSubmit"
    >
        <a-form :form="form">
            <a-form-item v-bind="formItemLayout" label="房型信息">
                <span>{{ currentOrderRoom.roomType }}</span>
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
            <a-form-item v-bind="formItemLayout" label="注">
                <span>酒店内部订单无法使用优惠~</span>
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
            roomNum:1,
        }
    },
    computed: {
        ...mapGetters([
            'orderModalVisible',
            'currentOrderRoom',
            'managerHotelId',
            'managerHotelInfo',
            'userId',
        ]),

    },
    beforeCreate() {
        this.form = this.$form.createForm(this, { name: 'orderModal' });
    },
    methods: {
        ...mapMutations([
            'set_orderModalVisible',
            'set_managerId'
        ]),
        ...mapActions([
            'addOrder',
            'getHotelByManagerId'
        ]),
        cancelOrder() {
            this.set_orderModalVisible(false)
            this.totalPrice = ''
            this.roomNum = 1
            this.form.resetFields()
        },
        moment,
        disabledDate(current) {
            // Can not select days before today and today
            return current && current < moment().startOf('day');
        },
        changeDate(v) {
            this.totalPrice = this.form.getFieldValue('roomNum') * moment(v[1]).diff(moment(v[0]), 'day') * Number(this.currentOrderRoom.price)
        },
        changePeopleNum(v){
        },
        changeRoomNum(v) {
            this.roomNum =  Number(v)
            if(this.totalPrice != '') {
                this.totalPrice = Number(v) * Number(this.currentOrderRoom.price) * moment(this.form.getFieldValue('date')[1]).diff(moment(this.form.getFieldValue('date')[0]), 'day')
            }
        },
        handleSubmit(e) {
            e.preventDefault();
            this.form.validateFieldsAndScroll((err, values) => {
                if (!err) {
                    const data = {
                        hotelId: this.managerHotelId,
                        hotelName: this.managerHotelInfo.hotelName,
                        userId: Number(this.userId),
                        roomId: this.currentOrderRoom.id,
                        checkInDate: moment(this.form.getFieldValue('date')[0]).format('YYYY-MM-DD'),
                        checkOutDate: moment(this.form.getFieldValue('date')[1]).format('YYYY-MM-DD'),
                        roomType: this.currentOrderRoom.roomType == '大床房' ? 'BigBed' : this.currentOrderRoom.roomType == '双床房' ? 'DoubleBed' : 'Family',
                        roomNum: this.form.getFieldValue('roomNum'),
                        peopleNum: this.form.getFieldValue('peopleNum'),
                        haveChild: this.form.getFieldValue('haveChild'),
                        createDate: '',
                        price: this.totalPrice
                    }
                    this.addOrder(data)
                    this.form.resetFields()

                }
            });
        },
    },
}
</script>