<template>
    <a-modal
            :visible="couponDetailVisible"
            title="优惠详情"
            @cancel = "finishLooking"
            :footer="null"
    >
        <a-descriptions layout="vertical" bordered :column="2">
            <a-descriptions-item label="编号">{{couponDetail.id}}</a-descriptions-item>
            <a-descriptions-item label="名称">{{couponDetail.couponName}}</a-descriptions-item>
            <a-descriptions-item label="类型">
                <span v-if="couponDetail.couponType==1">生日特惠</span>
                <span v-if="couponDetail.couponType==2">多间特惠</span>
                <span v-if="couponDetail.couponType==3">满减特惠</span>
                <span v-if="couponDetail.couponType==4">限时特惠</span>
                <span v-if="couponDetail.couponType==5">节日特惠</span>
                <span v-if="couponDetail.couponType==6">企业特惠</span>
            </a-descriptions-item>
            <a-descriptions-item label="描述">{{couponDetail.description}}</a-descriptions-item>
            <a-descriptions-item label="状态" :span="2">
                <a-badge v-if="couponDetail.status==1" status="success" text="有效"></a-badge>
                <a-badge v-if="couponDetail.status!=1" status="error" text="失效"></a-badge>
            </a-descriptions-item>
            <a-descriptions-item label="多间门槛" :span="2" v-if="couponDetail.roomNum">{{couponDetail.roomNum}} 间</a-descriptions-item>
            <a-descriptions-item label="开始时间" v-if="couponDetail.startTime">{{couponDetail.startTime}}</a-descriptions-item>
            <a-descriptions-item label="结束时间" v-if="couponDetail.endTime">{{couponDetail.endTime}}</a-descriptions-item>
            <a-descriptions-item label="满减门槛" v-if="couponDetail.targetMoney">{{couponDetail.targetMoney}}</a-descriptions-item>
            <a-descriptions-item label="满减额度" v-if="couponDetail.discountMoney">{{couponDetail.discountMoney}}</a-descriptions-item>
            <a-descriptions-item label="折扣力度" :span="2" v-if="couponDetail.discount">{{(couponDetail.discount*10).toFixed(1)}} 折</a-descriptions-item>
        </a-descriptions>
    </a-modal>
</template>

<script>
    import { mapGetters, mapMutations, mapActions } from 'vuex'
    export default {
        name: "couponDetailModal",
        data(){
            return{

            }
        },
        computed: {
            ...mapGetters([
                'couponDetailVisible',
                'couponDetail'
            ])
        },
        mounted() {
        },
        methods:{
            ...mapMutations([
                'set_couponDetailVisible',
                'set_couponDetail',
            ]),
            ...mapActions([]),
            finishLooking(){
                this.set_couponDetailVisible(false)
                //延迟更新数据，避免用户看到空白
                var that = this
                setTimeout(function () {
                    that.set_couponDetail({})
                },200)
            },
        },
    }
</script>

<style scoped>

</style>

