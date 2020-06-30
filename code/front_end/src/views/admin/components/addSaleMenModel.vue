<template>
    <a-modal
            :visible="addSaleMenModelVisible"
            title="添加用户"
            cancelText="取消"
            okText="确定"
            @cancel="cancel"
            @ok="handleSubmit"
    >
        <a-Form :form="form">
            <a-form-item v-bind="formItemLayout" label="用户名">
                <a-input
                        v-decorator="[
                        'username',
                        { rules: [{required: true, message: '请输入用户邮箱', }] }
                    ]"
                />
            </a-form-item>
            <a-form-item v-bind="formItemLayout" label="手机号">
                <a-input
                        v-decorator="[
                        'phoneNumber',
                        { rules: [{required: true, message: '请输入手机号', }] }
                    ]"
                />
            </a-form-item>
            <a-form-item v-bind="formItemLayout" label="用户邮箱">
                <a-input
                        v-decorator="[
                        'email',
                        { rules: [{required: true, message: '请输入用户邮箱', }] }
                    ]"
                />
            </a-form-item>
            <a-form-item v-bind="formItemLayout" label="密码">
                <a-input
                        type="password"
                        v-decorator="[
                        'password',
                        { rules: [{required: true, message: '请输入密码', }] }
                    ]"
                />
            </a-form-item >
            <a-form-item v-bind="formItemLayout" label="确认密码">
                <a-input
                        type="password"
                        v-decorator="[
                        'confirmPassword',
                        { rules: [{required: true, message: '请输入密码', },{ validator: this.handlePasswordCheck }] }
                    ]"
                />
            </a-form-item >
        </a-Form>
    </a-modal>
</template>
<script>
    import { mapGetters, mapMutations, mapActions } from 'vuex'
    export default {
        name: 'addSaleMenModel',
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
                'addSaleMenModelVisible',
                'saleMenList',
            ])
        },
        beforeCreate() {
            this.form = this.$form.createForm(this, { name: 'addSaleMenModel' });
        },
        mounted() {

        },
        methods: {
            ...mapMutations([
                'set_addSaleMenModelVisible',
                'set_addSaleMenParams',
            ]),
            ...mapActions([
                'getSaleMenList',
                'addSaleMen',
            ]),
            cancel() {
                this.set_addSaleMenModelVisible(false)
            },
            handlePasswordCheck (rule, value, callback) {
                const password = this.form.getFieldValue('password')
                console.log(password)
                if (value === undefined) {
                    callback(new Error('请输入密码'))
                }
                if (value && password && value.trim() !== password.trim()) {
                    callback(new Error('两次密码不一致'))
                }
                callback()
            },
            handleSubmit(e) {
                e.preventDefault();
                this.form.validateFieldsAndScroll((err, values) => {
                    if (!err) {
                        const data = {
                            email: this.form.getFieldValue('email'),
                            password: this.form.getFieldValue('password'),
                            phoneNumber:this.form.getFieldValue('phoneNumber'),
                            userName :this.form.getFieldValue('username'),
                            userType:'SaleMen',
                        }
                        this.set_addSaleMenParams(data)
                        this.addSaleMen()
                    }
                });
            },
        }
    }
</script>