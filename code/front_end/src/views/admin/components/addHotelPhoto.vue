<template>
    <a-modal
            :visible="addHotelPhotoVisible"
            title="添加照片"
            cancelText="取消"
            okText="完成"
            @cancel="cancel"
            @ok="finishUpload"
    >
        <a-Form :form="form">
            <a-form-item label = "photo" name = "photos">
                <a-upload
                        name="file"
                        :multiple="false"
                        :customRequest="customRequest"
                        :showUploadList = "false"
                        @change = "handleChange"

                        >
                    <a-button>
                        <a-icon type="to-top" />
                        选择图片
                    </a-button>
                </a-upload>
            </a-form-item>
        </a-Form>
    </a-modal>
</template>
<script>
    import { mapGetters, mapMutations, mapActions } from 'vuex'
    import {saveFileAPI} from "../../../api/admin";

    export default {
        name: 'addHotelPhoto',

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
               finish : false


            }
        },

        computed: {
            ...mapGetters([
                'addHotelPhotoVisible',
                'photoHotelId'
            ])
        },
        beforeCreate() {
            this.form = this.$form.createForm(this, { name: 'addHotelPhoto' });
        },
        mounted() {

        },
        methods: {
            ...mapMutations([
                'set_addHotelPhotoVisible',
                'set_PhotoHotelId',
            ]),
            ...mapActions([
                'saveFile',
            ]),
            cancel() {

                this.set_addHotelPhotoVisible(false)
            },
            finishUpload(){

                this.set_addHotelPhotoVisible(false)
                this.$emit('finished')

            },

            customRequest(data){ // 上传提交
                const formData = new FormData()
                formData.append('file', data.file)
                formData.append('hotelId',this.photoHotelId)
                formData.append('token', "token")
                const res = saveFileAPI(formData)
                if(res){
                    data.onSuccess()
                    this.finish=true
                }
            },
            handleChange(info) {
                if(this.finish){
                info.file.status='done'
                }

                if (info.file.status === 'done') {
                    this.$message.success(`${info.file.name} file uploaded successfully`);
                } else if (info.file.status === 'error') {
                    this.$message.error(`${info.file.name} file upload failed.`);
                }
            },


        }
    }
</script>