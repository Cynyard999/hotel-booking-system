<template>
    <div class="room-list">
        <div class="filter">

        </div>
        <div class="list">
            <a-table
                :columns="columns"
                :dataSource="rooms"
                :pagination="false"
            >
                <span slot="price" slot-scope="text">
                    <span>￥{{ text }}</span>
                </span>
                <span slot="action" slot-scope="record">
                    <a-button type="primary" @click="order(record)">预定</a-button>
                </span>
            </a-table>
        </div>
        <OrderModal></OrderModal>
    </div>
</template>
<script>
import { mapGetters, mapActions, mapMutations } from 'vuex'
import OrderModal from './orderModal'
const columns = [
    {  
      title: '房型',
      dataIndex: 'roomType',
      key: 'roomType',
      filters: [
            {
                text: '家庭房',
                value: '家庭房',
            },
            {
                text: '大床房',
                value: '大床房',
            },
            {
                text: '双床房',
                value: '双床房',
            },
        ],
        onFilter: (value, record) => record.roomType.indexOf(value) === 0,
    },
    {
      title: '房价',
      key: 'price',
      dataIndex: 'price',
      defaultSortOrder: 'descend',
      sorter: (a, b) => a.price - b.price,
      scopedSlots: { customRender: 'price'}
    },
    {
        title: '剩余房间数量',
        key: 'curNum',
        dataIndex: 'curNum',
    },
    {
      title: '操作',
      key: 'action',
      scopedSlots: { customRender: 'action' },
    },
  ];
export default {
    name:'roomList',
    props: {
        rooms: {
            type: Array
        }
    },
    data() {
        return {
            columns,
        }
    },
    components: {
        OrderModal
    },
    computed: {
        ...mapGetters([
            'orderModalVisible'
        ])
    },
    monuted() {

    },
    methods: {
        ...mapMutations([
            'set_orderModalVisible',
            'set_currentOrderRoom'
        ]),
        ...mapActions([

        ]),
        order(record) {
            this.set_currentOrderRoom(record)
            this.set_orderModalVisible(true)
        }
    }
}
</script>