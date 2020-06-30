<template>
    <a-modal
        :visible="addCouponVisible"
        title="添加优惠策略"
        cancelText="取消"
        okText="确定"
        @cancel="cancel"
        @ok="handleSubmit"
    >
        <!-- 这里是添加策略模态框区域，请编写表单 -->
        <a-form :form="form" style="margin-top: 30px" v-bind="formItemLayout">
            <a-form-item label="优惠券类型" v-bind="formItemLayout">
                <a-select
                        v-decorator="[
                    'couponType',
                    { rules: [{ required: true, message: '请选择优惠券类型' }] }]"
                        @change="changeType"
                >
                    <a-select-option value="2">多间特惠</a-select-option>
                    <a-select-option value="3">满减特惠</a-select-option>
                    <a-select-option value="4">限时特惠</a-select-option>
                    <a-select-option value="5">节日特惠</a-select-option>
                </a-select>
            </a-form-item>

            <a-form-item label="券名" v-bind="formItemLayout">
                <a-input
                        placeholder="请填写券名"
                        v-decorator="['couponName', { rules: [{ required: true, message: '请填写券名' }] }]"
                />
            </a-form-item>

            <a-form-item label="优惠简介" v-bind="formItemLayout">
                <a-input
                        type="textarea"
                        :rows="4"
                        placeholder="请填写优惠简介"
                        v-decorator="['description', { rules: [{ required: true, message: '请填写优惠简介'}] }]"
                />
            </a-form-item>

            <a-form-item label="房间数量" v-bind="formItemLayout" v-if="type==2">
                <a-input
                        type="number"
                        placeholder="请填写使用优惠所需房件数量"
                        v-decorator="['roomNum',{rules:[{required: true, message: '请填写房间数量'}]}]"
                />
            </a-form-item>

            <a-form-item label="优惠时间" v-bind="formItemLayout" v-if="type==4||type==5">
                <a-range-picker
                        format="YYYY-MM-DD"
                        v-decorator="[
                        'date',
                        {
                            rules: [{ required: true, message: '请选择入住时间' }]
                        }
                    ]"
                        :placeholder="['入住日期','退房日期']"
                />
            </a-form-item>

            <a-form-item label="优惠折扣" v-bind="formItemLayout" v-if="type!=3">
                <a-input
                        placeholder="折扣用小数表示，如九折表示为0.9"
                        v-decorator="['discount',{rules:[{required: true, message: '请填写优惠折扣'},{validator: this.changeKey}]}]"
                />
            </a-form-item>

            <a-form-item label="优惠门槛" v-bind="formItemLayout" v-if="type==3">
                <a-input
                        type="number"
                        placeholder="请填写优惠门槛"
                        v-decorator="['targetMoney', { rules: [{ required: true, message: '请填写优惠门槛'}] }]"
                />
            </a-form-item>

            <a-form-item label="优惠金额" v-bind="formItemLayout" v-if="type==3">
                <a-input
                        type="number"
                        placeholder="请填写优惠金额"
                        v-decorator="['discountMoney', { rules: [{ required: true, message: '请填写优惠金额'}] }]"
                />
            </a-form-item>

        </a-form>
    </a-modal>
</template>
<script>
import { mapGetters, mapMutations, mapActions } from 'vuex'
const moment = require('moment')
export default {
    name: 'addCouponModal',
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
            type: 0,
        }
    },
    computed: {
        ...mapGetters([
            'managerHotelId',
            'addCouponVisible',
        ])
    },
    beforeCreate() {
        // 表单名默认为“form”
        this.form = this.$form.createForm(this, { name: 'addCouponModal' });
    },
    mounted() {

    },
    methods: {
        ...mapMutations([
            'set_addCouponVisible',
            'set_couponType',
            'set_couponName',
            'set_couponDescription',
            'set_couponRoomNum',
            'set_couponTargetMoney',
            'set_couponDiscountMoney',
            'set_couponDiscount',
            'set_couponStartTime',
            'set_couponEndTime',
            'set_couponHotelId'
        ]),
        ...mapActions([
            'addHotelCoupon'
        ]),
        cancel() {
            this.set_addCouponVisible(false)
            this.form.resetFields()
        },
        changeType(v){
            this.type = v
            console.log(this.type)
        },
        handleSubmit(e) {
            e.preventDefault();
            this.form.validateFieldsAndScroll((err, values) => {
                if (!err) {
                    this.set_couponDescription(this.form.getFieldValue("description"))
                    this.set_couponName(this.form.getFieldValue("description"))
                    this.set_couponHotelId(Number(this.managerHotelId))
                    this.set_couponType(this.type)
                    if(this.type==="2"){
                        this.set_couponRoomNum(Number(this.form.getFieldValue("roomNum")))
                        this.set_couponDiscount(Number(this.form.getFieldValue("discount")))
                    }
                    else if(this.type==="3"){
                        this.set_couponTargetMoney(Number(this.form.getFieldValue("targetMoney")))
                        this.set_couponDiscountMoney(Number(this.form.getFieldValue("discountMoney")))
                    }
                    else if(this.type==="4"||this.type==="5"){
                        this.set_couponStartTime(this.form.getFieldValue("date")[0].format('YYYY-MM-DD'))
                        this.set_couponEndTime(this.form.getFieldValue("date")[1].format('YYYY-MM-DD'))
                        this.set_couponDiscount(Number(this.form.getFieldValue("discount")))
                    }
                    this.addHotelCoupon()
                    this.form.resetFields()
                }
            });
        },
        changeKey(rule,value,callback){
            let rgx = /0.[0-9]*[1-9]+/
            if(rgx.test(value)&&Number(value)<1){
                callback()
            }
            else {
                callback(new Error("折扣请填写小于1的小数！"))
            }
        }
    }
}
</script>