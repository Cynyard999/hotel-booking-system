<template>
    <a-modal
            :visible="adminAddHotelModelVisible"
            title="添加酒店"
            cancelText="取消"
            okText="确定"
            @cancel="cancel"
            @ok="handleSubmit"
    >
        <a-form :form="form" style="margin-top: 30px" v-bind="formItemLayout">
            <a-form-item label="酒店名">
                <a-input
                        placeholder="请填写酒店名称"
                        v-decorator="['hotelName', { rules: [{ required: true, message: '请填写酒店名称' }] }]"
                />
            </a-form-item>
            <a-form-item label="酒店地址" v-bind="formItemLayout">
                <a-input
                        placeholder="请填写酒店地址"
                        v-decorator="['address', { rules: [{ required: true, message: '请填写酒店地址' }] }]"
                />
            </a-form-item>
            <a-form-item label="商圈" v-bind="formItemLayout">
                <a-select
                        v-decorator="[
                    'bizRegion',
                    { rules: [{ required: true, message: '请选择商圈' }] }]"
                        @change="changeRegion"
                >
                    <a-select-option value="XinJieKou">新街口</a-select-option>
                    <a-select-option value="JiangNing">江宁</a-select-option>
                    <a-select-option value="ZhuJiangLu">珠江路</a-select-option>
                    <a-select-option value="FuZiMiao">夫子庙</a-select-option>
                    <a-select-option value="HeXi">河西</a-select-option>
                    <a-select-option value="XianLin">仙林</a-select-option>
                    <a-select-option value="JiangBei">江北</a-select-option>
                </a-select>
            </a-form-item>
            <a-form-item label="酒店星级" v-bind="formItemLayout">
                <a-select
                        v-decorator="[
                    'hotelStar',
                    { rules: [{ required: true, message: '请选择酒店星级' }] }]"
                        @change="changeStar"
                >
                    <a-select-option value="Three">三星级</a-select-option>
                    <a-select-option value="Four">四星级</a-select-option>
                    <a-select-option value="Five">五星级</a-select-option>
                </a-select>
            </a-form-item>
            <a-form-item label="手机号" v-bind="formItemLayout">
                <a-input
                        placeholder="请填写手机号"
                        v-decorator="['phoneNumber', { rules: [{ required: true, message: '请输入手机号' }] }]"
                />
            </a-form-item>
            <a-form-item label="酒店简介" v-bind="formItemLayout">
                <a-input
                        type="textarea"
                        :rows="2"
                        placeholder="请填写酒店简介(不超过20字)"
                        v-decorator="['description', { rules: [{ required: true, message: '请填写酒店简介'},{max:20,message: '长度不能超过20'}] }]"
                />
            </a-form-item>
            <a-form-item label="酒店详情" v-bind="formItemLayout">
                <a-input
                        type="textarea"
                        :rows="5"
                        placeholder="请填写酒店详情"
                        v-decorator="['detail', { rules: [{ required: true, message: '请填写酒店详情' }] }]"
                />
            </a-form-item>

        </a-form>
    </a-modal>
</template>
<script>
    import { mapGetters, mapMutations, mapActions } from 'vuex'


    export default {
        name: 'adminAddHotelModel',
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
            }
        },
        computed: {
            ...mapGetters([
                'adminAddHotelParams',
                'adminAddHotelModelVisible',
            ]),
        },
        beforeCreate() {
            this.form = this.$form.createForm(this, { name: 'adminAddHotelModal' });
        },
        mounted() {

        },
        methods: {
            ...mapMutations([
                'set_adminAddHotelParams',
                'set_adminAddHotelModelVisible',
                'set_photoHotelId'
            ]),
            ...mapActions([
                'adminAddHotel',
                'saveFile',
            ]),
            cancel() {
                this.set_adminAddHotelModelVisible(false)
            },
            changeStar(v){

            },
            changeRegion(v){

            },
            handleSubmit(e) {

                e.preventDefault();
                this.form.validateFieldsAndScroll((err, values) => {
                    if (!err) {
                        const data = {
                            hotelName: this.form.getFieldValue('hotelName'),
                            description: this.form.getFieldValue('description'),
                            address: this.form.getFieldValue('address'),
                            phoneNum: this.form.getFieldValue('phoneNumber'),
                            hotelStar: this.form.getFieldValue('hotelStar'),
                            bizRegion:this.form.getFieldValue('bizRegion'),
                            detail:this.form.getFieldValue("detail"),
                            rate:4.0,
                        }
                        this.set_adminAddHotelParams(data)
                        this.adminAddHotel()
                    }
                });
            },



        }
    }
</script>