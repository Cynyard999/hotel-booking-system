<template>
  <a-modal
      :visible="makeEvaluationVisible"
      :title="title"
      cancelText="取消"
      okText="确定"
      @cancel="cancel"
      @ok="handleSubmit"
  >
    <a-form :form="form" style="margin-top: 30px" v-bind="formItemLayout">
      <a-form-item label="总体评分">
        <a-rate v-decorator="['rate', { initialValue: 4.0 }]" allow-half />
      </a-form-item>
      <a-form-item label="入住评价">
        <a-textarea
            placeholder="请填写本次入住评价"
            v-decorator="['evaluation', { rules: [{ required: true, message: '评价不能为空哦' }] }]"
            :auto-size="{ minRows: 2, maxRows: 6 }"
        />
      </a-form-item>
    </a-form>
  </a-modal>
</template>
<script>
  import { mapGetters, mapMutations, mapActions } from 'vuex'
  export default {
    name: 'makeEvaluationModal',
    data() {
      return {
        title:'',
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
        rateSuccess:'',
        evaluateSuccess:''
      }
    },
    computed: {
      ...mapGetters([
        'makeEvaluationVisible',
        'evaluatedHotelName'
      ])
    },
    beforeCreate() {
      this.form = this.$form.createForm(this, { name: 'makeEvaluationModal' });
    },
    mounted() {
      this.title = this.evaluatedHotelName+" 入住评价"
    },
    methods: {
      ...mapMutations([
        'set_makeEvaluationVisible',
      ]),
      ...mapActions([
        'evaluate',
        'rate',
      ]),
      cancel() {
        this.set_makeEvaluationVisible(false)
        this.form.resetFields()
      },
      handleSubmit() {
        this.form.validateFieldsAndScroll((err) => {
          if (!err) {
            let that = this
            var res = that.evaluate(this.form.getFieldValue("evaluation"))
            res.then(function (result) {that.evaluateSuccess = result;})
            res = that.rate(Number(this.form.getFieldValue("rate")))
            res.then(function (result) {that.rateSuccess = result;})
            this.timer = setTimeout(()=>{   //设置延迟执行
              if (that.evaluateSuccess&&this.rateSuccess){
                this.$notification.open({
                  message: '评价成功',
                  description:'欢迎下次入住！',
                  duration: 1,
                  icon: <a-icon type="smile" style="color: #108ee9" />
              })
              }
            },500);
            that.form.resetFields()
          }
        });
        this.set_makeEvaluationVisible(false);
      },

    }
  }
</script>