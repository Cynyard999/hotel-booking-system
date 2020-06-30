<template>
    <a-modal
        :visible="addWebsiteCouponModalVisible"
        title="添加网站优惠"
        cancelText="取消"
        okText="确定"
        @cancel = "cancel"
        @ok = "handleSubmit"
    >
        <a-form :form = "form" style="..." v-bind="formItemLayout">
            <a-form-item label="优惠券类型" v-bind="formItemLayout">
                <a-select
                        v-decorator="[
                    'couponType',
                    { rules: [{ required: true, message: '请选择优惠券类型' }] }]"
                        @change="changeType"
                >
                    <a-select-option value="1">生日特惠</a-select-option>
                    <a-select-option value="6">企业特惠</a-select-option>
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

            <a-form-item label="房间数量" v-bind="formItemLayout" v-if="type==6">
                    <a-input
                            type="number"
                            placeholder="请填写使用优惠所需房件数量"
                            v-decorator="['roomNum',{rules:[{required: true, message: '请填写房间数量'}]}]"
                    />
            </a-form-item>

            <a-form-item label="优惠折扣" v-bind="formItemLayout">
                    <a-input
                            placeholder="折扣用小数表示，如九折表示为0.9"
                            v-decorator="['discount',{rules:[{required: true, message: '请填写优惠折扣'},{validator: this.changeKey}]}]"
                    />
                </a-form-item>

        </a-form>
    </a-modal>
</template>

<script>
    import { mapGetters, mapMutations, mapActions } from 'vuex'
    export default {
        name: "addCouponModal",
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
                type: 0
            }
        },
        computed:{
            ...mapGetters([
                'addWebsiteCouponModalVisible',
            ])
        },
        beforeCreate() {
            // 表单名默认为“form”
            this.form = this.$form.createForm(this, { name: 'addCouponModal' });
        },
        mounted() {

        },
        methods:{
            ...mapMutations([
               'set_addWebsiteCouponModalVisible',
                'set_couponName',
                'set_couponDescription',
                'set_couponType',
                'set_couponDiscount',
                'set_couponRoomNum'
            ]),
            ...mapActions([
                'addWebsiteCoupon'
            ]),
            cancel() {
                this.set_addWebsiteCouponModalVisible(false)
                this.form.resetFields()
            },
            changeType(v){
                this.type = v
                console.log(this.type)
            },
            handleSubmit(e){
                e.preventDefault();
                this.form.validateFieldsAndScroll((err, values) => {
                    if (!err) {
                        this.set_couponName(this.form.getFieldValue("couponName"))
                        this.set_couponDescription(this.form.getFieldValue("description"))
                        this.set_couponType(this.form.getFieldValue("couponType"))
                        this.set_couponDiscount(this.form.getFieldValue("discount"))
                        if(this.type===6){
                            this.set_couponRoomNum(this.form.getFieldValue("roomNum"))
                        }
                        this.addWebsiteCoupon()
                        this.form.resetFields()
                    }
                });
            },
            changeKey(rule,value,callback){
                let rgx = /0.[0-9]*[1-9]+/
                if(rgx.test(value)&& Number(value)<1){
                    callback()
                }
                else {
                    callback(new Error("折扣请填写小于1的小数！"))
                }
            }
        }
    }
</script>