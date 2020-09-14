<template>
    <div class="manageWebsite-wrapper">
        <a-tabs>
            <a-tab-pane tab="网站优惠管理" key="1">
                <div style="width: 100%; text-align: right; margin:20px 0">
                    <a-button type="primary" @click="addCoupon"><a-icon type="plus" />添加网站优惠</a-button>
                </div>
                <a-table
                    :columns="columns1"
                    :dataSource="websiteCouponList"
                    bordered
                >
                    <span slot="action" slot-scope="record">
                        <a-button type="primary" size="small" @click="checkCouponDetail(record.id)">查看</a-button>
                        <a-divider type="vertical" ></a-divider>
                        <a-popconfirm
                            title="确认想删除网站优惠吗？"
                            @confirm="deleteCoupon(record.id)"
                            okText="确定"
                            cancelText="取消">
                            <a-button type="danger" size="small">删除优惠</a-button>
                        </a-popconfirm>
                    </span>
                </a-table>
            </a-tab-pane>

            <a-tab-pane tab="异常订单管理" key="2">
                <a-table
                    :columns="columns2"
                    :data-source="exceptionOrderList"
                    bordered
                >
                    <span slot="price" slot-scope="text">
                        <span>￥{{ text }}</span>
                    </span>
                    <span slot="roomType" slot-scope="text">
                        <span v-if="text == 'BigBed'">大床房</span>
                        <span v-if="text == 'DoubleBed'">双床房</span>
                        <span v-if="text == 'Family'">家庭房</span>
                    </span>
                    <span slot="action" slot-scope="record">
                        <a-button type="primary" size="small" @click="changeStatus(record)"
                        v-if="record.orderState=='已申诉'">接受订单申诉</a-button>
                    </span>
                </a-table>
            </a-tab-pane>

            <a-tab-pane tab="信用值充值" key="3">
                <a-form :form="form" style="...">
                    <a-form-item lable="电子邮箱">
                        <a-input
                            placeholder = "请填写电子邮箱地址"
                            v-decorator = "['emailAdd', { rules: [{ required: true, message: '请输入电子邮箱' }] }]"
                        />
                    </a-form-item>
                    <a-form-item lable="充值信用值" style="...">
                        <a-input
                            placeholder = "请填写充值的信用值"
                            v-decorator = "['credit', { rules: [{ required: true, message: '请输入充值量' },{ validator: this.handleCredit }] }]"
                        />
                    </a-form-item>
                    <a-form-item style="...">
                        <a-button
                            size="large"
                            type="primary"
                            class="ok-button"
                            @click = "handleCharge"
                        >确定
                        </a-button>
                    </a-form-item>
                </a-form>
            </a-tab-pane>
        </a-tabs>
        <AddWebsiteCouponModal></AddWebsiteCouponModal>
        <CouponDetailModal></CouponDetailModal>
    </div>
</template>

<script>
    import { mapGetters, mapMutations, mapActions } from 'vuex'
    import AddWebsiteCouponModal from './components/addWebsiteCouponModal'
    import CouponDetailModal from "../hotelManager/components/couponDetailModal";
    const moment = require("moment")
    const columns1 = [
        {
            title:"优惠名称",
            dataIndex:"couponName",
        },
        {
            title:"优惠描述",
            dataIndex: "description"
        },
        {
            title:"操作",
            key:'action',
            scopedSlots: { customRender: 'action' },
        }
    ];
    const columns2 = [
        {
            title: '订单号',
            dataIndex: 'id',
        },
        {
            title: '酒店名',
            dataIndex: 'hotelName',
        },
        {
            title: '房型',
            dataIndex: 'roomType',
            scopedSlots: { customRender: 'roomType' }
        },
        {
            title: '入住时间',
            dataIndex: 'checkInDate',
            scopedSlots: { customRender: 'checkInDate' }
        },
        {
            title: '离店时间',
            dataIndex: 'checkOutDate',
            scopedSlots: { customRender: 'checkOutDate' }
        },
        {
            title: '入住人数',
            dataIndex: 'peopleNum',
        },
        {
            title: '房价',
            dataIndex: 'price',
            scopedSlots: {customRender: 'price'}
        },
        {
            title: '操作',
            key: 'action',
            scopedSlots: { customRender: 'action' },
        },
    ];
    export default {
        name: "manageWebsite",
        data(){
            return {
                formLayout: 'horizontal',
                pagination: {},
                columns1,
                columns2,
                data:[],
                form: this.$form.createForm(this, { name: 'manageWebsite' }),
            }
        },
        components: {
            AddWebsiteCouponModal,
            CouponDetailModal
        },
        computed:{
            ...mapGetters([
                'exceptionOrderList',
                'websiteCouponList',
                'addWebsiteCouponModalVisible',
            ])
        },
        async mounted(){
            await this.getWebsiteCoupons()
            await this.getAllExceptionOrders()
        },
        methods:{
            ...mapMutations([
                'set_activeOrderId',
                'set_activeEmailAddress',
                'set_activeCredit',
                'set_addWebsiteCouponModalVisible',
                'set_couponDetail',
                'set_couponDetailVisible',
            ]),
            ...mapActions([
                "getAllExceptionOrders",
                "getWebsiteCoupons",
                "processExceptionOrder",
                "chargeCredit",
                "deleteWebsiteCoupon"
            ]),
            addCoupon(){
                this.set_addWebsiteCouponModalVisible(true)
            },
            deleteCoupon(couponId){
                this.deleteWebsiteCoupon(couponId)
            },
            checkCouponDetail(couponId){
                let coupon
                for(let i in this.websiteCouponList){
                    if(this.websiteCouponList[i].id==couponId){
                        coupon = this.websiteCouponList[i]
                        break
                    }
                }
                if(coupon){
                    this.set_couponDetail(coupon)
                    this.set_couponDetailVisible(true)
                }
                else{
                    console.log('遍历couponlist失败',coupon)
                }
            },
            changeStatus(record){
                this.set_activeOrderId(record.id)
                this.processExceptionOrder()
            },
            handleCharge(){
                this.form.validateFieldsAndScroll((err ,values)=>{
                    if(!err){
                        const data1 = this.form.getFieldValue("emailAdd")
                        const data2 = Number(this.form.getFieldValue("credit"))
                        this.set_activeEmailAddress(data1)
                        this.set_activeCredit(data2)
                        this.chargeCredit()
                    }
                });
                this.form.resetFields()
            },
            handleCredit(rule, value, callback){
                const credit = Number(this.form.getFieldValue("credit"))
                console.log(credit)
                if (value === undefined) {
                    callback(new Error('请输入充值信用值'))
                }
                if(credit>100){
                    callback(new Error('充值超过上限，不得100'))
                }
                callback()
            }
            ,
        },
    }
</script>

<style scoped lang="less">
    .manageWebsite-wrapper {
        padding: 50px;
        .chart {
            display: flex;
            align-items: center;
            justify-content: space-between;
            margin-top: 20px
        }
    }
</style>
<style lang="less">
    .manageWebsite-wrapper {
        .ant-tabs-bar {
            padding-left: 30px
        }
    }
    button.ok-button{
        padding: 0 15px;
        font-size: 16px;
        height: 40px;
        width: 20%;
    }
</style>
<style lang="less">

</style>