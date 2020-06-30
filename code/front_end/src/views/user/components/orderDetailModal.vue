<template>
    <a-modal
        :visible="userOrderDetailVisible"
        title="订单详情"
        okText="完成"
        cancelText = "取消"
        @cancel = "finishLooking"
        @ok = "finishLooking"
    >
        <a-descriptions bordered layout="vertical">
            <a-descriptions-item label="订单号">{{userOrderDetail.id}}</a-descriptions-item>
            <a-descriptions-item label="酒店名称">{{userOrderDetail.hotelName}}</a-descriptions-item>
            <a-descriptions-item label="创建日期">{{userOrderDetail.createDate}}</a-descriptions-item>
            <a-descriptions-item label="入住日期">{{userOrderDetail.checkInDate}}</a-descriptions-item>
            <a-descriptions-item label="退房日期">{{userOrderDetail.checkOutDate}}</a-descriptions-item>
            <a-descriptions-item label="房间类型">
                <span v-if="userOrderDetail.roomType=='BigBed'">大床房</span>
                <span v-if="userOrderDetail.roomType=='DoubleBed'">双床房</span>
                <span v-if="userOrderDetail.roomType=='Family'">家庭房</span>
            </a-descriptions-item>
            <a-descriptions-item label="房间数量">{{userOrderDetail.roomNum}}</a-descriptions-item>
            <a-descriptions-item label="入住人数">{{userOrderDetail.peopleNum}}</a-descriptions-item>
            <a-descriptions-item label="价格">{{userOrderDetail.price}}</a-descriptions-item>
            <a-descriptions-item label="订单状态">
                <a-badge v-if="userOrderDetail.orderState=='已预订'" status="processing" text="已预订"></a-badge>
                <a-badge v-if="userOrderDetail.orderState=='已入住'" status="success" text="已入住"></a-badge>
                <a-badge v-if="userOrderDetail.orderState=='已完成'" status="success" text="已完成"></a-badge>
                <a-badge v-if="userOrderDetail.orderState=='已撤销'" status="warning" text="已撤销"></a-badge>
                <a-badge v-if="userOrderDetail.orderState=='异常'" status="error" text="异常"></a-badge>
                <a-badge v-if="userOrderDetail.orderState=='已申诉'" status="error" text="已申诉"></a-badge>
            </a-descriptions-item>
        </a-descriptions>
    </a-modal>
</template>

<script>
    import { mapGetters, mapMutations, mapActions } from 'vuex'
    export default {
        name: "orderDetailModal",
        data(){
            return{

            }
        },
        computed: {
            ...mapGetters([
                'userOrderDetailVisible',
                'userOrderDetail'
            ])
        },
        mounted() {
        },
        methods:{
          ...mapMutations([
              'set_userOrderDetailVisible',
              'set_userOrderDetail',
          ]),
          ...mapActions([]),
          finishLooking(){
              this.set_userOrderDetailVisible(false)
              this.set_userOrderDetail({})
          }
        },
    }
</script>

<style scoped>

</style>