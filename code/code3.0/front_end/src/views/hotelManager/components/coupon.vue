<template>
   <div>
    <a-modal
        :visible="couponVisible"
        title="优惠策略"
        width="900px"
        :footer="null"
        @cancel="cancel"
    >
        <!-- 这里是模态框内容区域，请编写列表代码与添加策略按钮 -->
        <div style="width: 100%; text-align: right; margin:20px 0">
            <a-button type="primary" @click="addCoupon"><a-icon type="plus" />添加优惠策略</a-button>
        </div>
        <a-table class="coupon" :columns="columns" :dataSource="couponList" bordered>
            <a-tag color= "red" slot="couponType" slot-scope="text">
                {{toType(text)}}
            </a-tag>
            <template slot="couponName" slot-scope="text">
                <span style="color: #1890ff">{{ text }}</span>
            </template>
        </a-table>

    </a-modal>
    <AddCoupon></AddCoupon>
   </div>
</template>

<script>
import { mapGetters, mapMutations, mapActions } from 'vuex'
import AddCoupon from './addCoupon'

const columns = [
    // 这里定义列表头
    {
        title: '优惠类型',
        dataIndex: 'couponType',
        key: 'couponType',
        scopedSlots: { customRender: 'couponType' },
    },
    {
        title: '优惠券名称',
        dataIndex: 'couponName',
        key: 'couponName',
        scopedSlots: { customRender: 'couponName' },
    },
    {
        title: '折扣',
        dataIndex: 'discount',
        key: 'discount',
    },
    {
        title: '优惠简介',
        dataIndex: 'description',
        key: 'description',
    },
    {
        title: '优惠金额',
        dataIndex: 'discountMoney',
        key: 'discountMoney',
    },
  ];
export default {
    name: 'coupon',
    data() {
        return {
            columns
        }
    },
    components: {
        AddCoupon,
    },
    computed: {
        ...mapGetters([
            'couponVisible',
            'couponList',
        ])
    },
    methods: {
        ...mapMutations([
            'set_addCouponVisible',
            'set_couponVisible',
        ]),
        ...mapActions([
            'getHotelCoupon'
        ]),
        cancel() {
            this.set_couponVisible(false)
        },
        addCoupon() {
            this.set_addCouponVisible(true),
            this.set_couponVisible(false)
        },
        toType(text){
            if (text === 1){
                return "生日特惠"
            }
            else if (text === 2){
                return "多间特惠"
            }
            else if (text === 3){
                return "满减特惠"
            }
            else if (text === 4){
                return "限时特惠"
            }
            else {
                return " "
            }
        }
    },
}
</script>
<style scoped>

</style>