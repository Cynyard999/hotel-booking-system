<template>
    <div class="manageHotel-wrapper">
        <a-tabs>
            <a-tab-pane tab="酒店信息" key="1">

                <a-form :form="form" style="margin-top: 30px">
                    <a-form-item label="酒店名称" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1  }">
                        <a-input
                                placeholder="请填写酒店名称"
                                v-decorator="['hotelName', { rules: [{ required: true, message: '请输入酒店名称' }] }]"
                                v-if="modify"
                        />
                        <span v-else>{{ managerHotelInfo.hotelName }}</span>
                    </a-form-item>
                    <a-form-item label="地址" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1  }">
                        <a-input
                                placeholder="请填写地址"
                                v-decorator="['address', { rules: [{ required: true, message: '请输入地址' }] }]"
                                v-if="modify"
                        />
                        <span v-else>{{ managerHotelInfo.address }}</span>
                    </a-form-item>
                    <a-form-item label="商圈" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1  }">
                        <a-select
                                placeholder="请选择商圈"
                                v-if="modify"
                                v-decorator="['bizRegion',{ rules: [{ required: true, message: '请选择商圈' }] }]"
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
                        <span v-else>
                            <span v-if="managerHotelInfo.bizRegion == 'XinJieKou'">新街口</span>
                            <span v-if="managerHotelInfo.bizRegion == 'JiangNing'">江宁</span>
                            <span v-if="managerHotelInfo.bizRegion == 'ZhuJiangLu'">珠江路</span>
                            <span v-if="managerHotelInfo.bizRegion == 'FuZiMiao'">夫子庙</span>
                            <span v-if="managerHotelInfo.bizRegion == 'HeXi'">河西</span>
                            <span v-if="managerHotelInfo.bizRegion == 'XianLin'">仙林</span>
                            <span v-if="managerHotelInfo.bizRegion == 'JiangBei'">江北</span>
                        </span>
                    </a-form-item>
                    <a-form-item label="评分" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }">
                        <span>{{ managerHotelInfo.rate }}</span>
                    </a-form-item>
                    <a-form-item label="酒店星级" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1  }">
                        <a-select
                                placeholder="请选择酒店星级"
                                v-if="modify"
                                v-decorator="['hotelStar',{ rules: [{ required: true, message: '请选择酒店星级' }] }]"
                                @change="changeStar"
                        >
                            <a-select-option value="Three">三星级</a-select-option>
                            <a-select-option value="Four">四星级</a-select-option>
                            <a-select-option value="Five">五星级</a-select-option>
                        </a-select>
                        <span v-else>
                            <span v-if="managerHotelInfo.hotelStar == 'Three'">三星级</span>
                            <span v-if="managerHotelInfo.hotelStar == 'Four'">四星级</span>
                            <span v-if="managerHotelInfo.hotelStar == 'Five'">五星级</span>
                        </span>
                    </a-form-item>
                    <a-form-item label="电话" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1  }">
                        <a-input
                                placeholder="请填写电话"
                                v-decorator="['phoneNum', { rules: [{ required: true, message: '请输入电话' }] }]"
                                v-if="modify"
                        />
                        <span v-else>{{ managerHotelInfo.phoneNum }}</span>
                    </a-form-item>
                    <a-form-item label="酒店简介" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1  }">
                        <a-input
                                placeholder="请填写酒店"
                                v-decorator="['description', { rules: [{ required: true, message: '请输入酒店' }] }]"
                                v-if="modify"
                        />
                        <span v-else>{{ managerHotelInfo.description }}</span>
                    </a-form-item>
                    <a-form-item label="酒店详情" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1  }">
                        <a-input
                                placeholder="请填写详情"
                                v-decorator="['detail', { rules: [{ required: true, message: '请输入详情' }] }]"
                                v-if="modify"
                        />
                        <span v-else>{{ managerHotelInfo.detail }}</span>
                    </a-form-item>
                    <a-form-item :wrapper-col="{ span: 12, offset: 5 }" v-if="modify">
                        <a-button type="primary" @click="saveModify">
                            保存
                        </a-button>
                        <a-button type="default" style="margin-left: 30px" @click="cancelModify">
                            取消
                        </a-button>
                    </a-form-item>
                    <a-form-item :wrapper-col="{ span: 8, offset: 4 }" v-else>
                        <a-button type="primary" @click="modifyInfo">
                            修改信息
                        </a-button>
                    </a-form-item>
                    <a-form-item label="酒店图片"  :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }">
                        <img
                                alt="example"
                                :src=url+managerHotelId+currentTime
                                referrerPolicy="no-referrer"
                                style="max-width:600px"
                        />
                    </a-form-item>
                    <a-form-item :wrapper-col="{ span: 8, offset: 4 }">
                        <a-button type="primary" @click="addHotelPhoto(managerHotelId)">
                            上传/修改图片
                        </a-button>
                    </a-form-item>
                </a-form>
            </a-tab-pane>

            <a-tab-pane tab="房间管理" key="2">
                <div style="width: 100%; text-align: right; margin:20px 0">
                    <a-button type="primary" @click="addRoom"><a-icon type="plus" />录入客房</a-button>
                </div>
                <a-table
                        :columns="rooms"
                        :dataSource="managerHotelInfo.rooms"
                        :rowKey='record=>record.id'
                >
                <span slot="price" slot-scope="text">
                    <span>￥{{ text }}</span>
                </span>
                    <span slot="action" slot-scope="record">
                    <a-button type="primary" @click="order(record)">预定</a-button>
                </span>
                </a-table>
            </a-tab-pane>

            <a-tab-pane tab="订单管理" key="3">
                <a-table
                        :columns="orders"
                        :dataSource="orderList"
                        bordered
                        :rowKey='record=>record.id'
                >
                    <span slot="price" slot-scope="text">
                        <span>￥{{ text }}</span>
                    </span>
                    <span slot="roomType" slot-scope="text">
                        <span v-if="text == 'BigBed'">大床房</span>
                        <span v-if="text == 'DoubleBed'">双床房</span>
                        <span v-if="text == 'Family'">家庭房</span>
                    </span>
                    <span slot="orderState" slot-scope="text">
                        <span>{{text}}</span>
                    </span>
                    <span slot="action" slot-scope="record">
                       <a-button type="primary" size="small" @click="executeO(record.id)"
                                 v-if="record.orderState==='已预订'||record.orderState==='异常'">入住</a-button>
                        <a-button type="danger" size="small" @click="checkout(record.id)"
                                  v-if="record.orderState==='已入住'">退房</a-button>
                    </span>
                </a-table>
            </a-tab-pane>

            <a-tab-pane tab="优惠管理" key="4">
                <div style="width: 100%; text-align: right; margin:20px 0">
                    <a-button type="primary" @click="addHotelCoupon"><a-icon type="plus" />添加优惠政策</a-button>
                </div>
                <a-table
                        :columns="coupons"
                        :dataSource="couponList"
                        bordered
                        :rowKey='record=>record.id'
                >
                    <span slot="orderState" slot-scope="text">{{text}}</span>
                    <span slot="action" slot-scope="record">
                        <a-button type="primary" size="small" @click="checkCouponDetail(record.id)">查看</a-button>
                        <a-divider type="vertical" ></a-divider>
                        <a-popconfirm
                                title="确认想删除酒店优惠吗？"
                                @confirm="delHotelCoupon(record.id)"
                                okText="确定"
                                cancelText="取消"
                        >
                            <a-button type="danger" size="small">删除优惠</a-button>
                        </a-popconfirm>
                    </span>
                </a-table>
            </a-tab-pane>
        </a-tabs>
        <AddHotelPhoto @finished = 'refresh($event)'></AddHotelPhoto>
        <AddRoomModal></AddRoomModal>
        <OrderModal></OrderModal>
        <AddCoupon></AddCoupon>
        <CouponDetailModal></CouponDetailModal>
    </div>
</template>
<script>
import { mapGetters, mapMutations, mapActions } from 'vuex'
import AddCoupon from './components/addCoupon'
import OrderModal from './components/orderModal'
import AddRoomModal from './components/addRoomModal'
import AddHotelPhoto from '../admin/components/addHotelPhoto'
import CouponDetailModal from './components/couponDetailModal'
const moment = require('moment')
const rooms = [
    {
        title: '房间id',
        dataIndex: 'id',
        key: 'id',
    },
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
        title: '总数',
        dataIndex: 'total',
        key: 'total',
    },
    {
        title: '空闲房间',
        dataIndex: 'curNum',
        key: 'curNum',
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
        title: '操作',
        key: 'action',
        scopedSlots: { customRender: 'action' },
    },
];
const orders = [
    {
        title: '订单号',
        dataIndex: 'id',
    },
    {
        title: '酒店名',
        dataIndex: 'hotelName',
    },
    {
        title: '房型',
        dataIndex: 'roomType',
        scopedSlots: { customRender: 'roomType' }
    },
    {
        title: '入住时间',
        dataIndex: 'checkInDate',
        scopedSlots: { customRender: 'checkInDate' }
    },
    {
        title: '离店时间',
        dataIndex: 'checkOutDate',
        scopedSlots: { customRender: 'checkOutDate' }
    },
    {
        title: '入住人数',
        dataIndex: 'peopleNum',
    },
    {
        title: '总价',
        dataIndex: 'price',
    },
    {
        title: '状态',
        filters: [{ text: '已预订', value: '已预订' }, { text: '已撤销', value: '已撤销' }, { text: '已入住', value: '已入住' },
            {text:'已完成',value:'已完成'},{text:'已申诉',value:'已申诉'}],
        onFilter: (value, record) => record.orderState.includes(value),
        dataIndex: 'orderState',
        scopedSlots: { customRender: 'orderState' }
    },
    {
        title: '操作',
        key: 'action',
        scopedSlots: { customRender: 'action' },
    },
];
const coupons = [
    {
        title:"优惠名称",
        dataIndex:"couponName",
    },
    {
        title:"优惠描述",
        dataIndex: "description",
    },
    {
        title:"操作",
        key:'action',
        scopedSlots: { customRender: 'action' },
    }
];
export default {
    name: 'manageHotel',

    data(){
        return {
            //三个数据表格room,order,coupon
            rooms,
            orders,
            coupons,
            //其他data
            formLayout: 'horizontal',
            form: this.$form.createForm(this, { name: 'manageHotel' }),
            modify: false,
            url:"https://hotel-booking-system.oss-cn-beijing.aliyuncs.com/",
            currentTime:"?timestamp=" + new Date().getTime()
        }
    },
    components: {
        //添加酒店图片
        AddHotelPhoto,
        //下订单
        OrderModal,
        //录入房间
        AddRoomModal,
        //添加优惠和查看优惠详情
        AddCoupon,
        CouponDetailModal
    },
    computed: {
        ...mapGetters([
            //酒店信息部分
            'managerHotelId',
            'managerHotelInfo',
            //酒店房间部分
            'addRoomModalVisible',
            'orderModalVisible',
            //酒店订单部分
            'orderList',
            //酒店优惠部分
            'couponList',
            'couponDetailVisible',
            'couponDetail'
        ]),
    },
    async mounted() {
        //酒店信息部分
        await this.set_managerId(Number(this.$route.params.managerId))
        await this.getHotelByManagerId()
        //酒店房间部分
        //酒店订单部分
        await this.getAllHotelOrders()
        //酒店优惠部分
        await this.getHotelCoupon()
    },
    beforeRouteUpdate(to, from, next) {
        //this.set_managerId(Number(to.params.hotelId))
        //this.getHotelByManagerId()
        //next()
    },
    methods: {
        ...mapMutations([

            //酒店信息部分
            'set_managerId',
            'set_activeHotelId',
            'set_photoHotelId',
            'set_addHotelPhotoVisible',
            //酒店房间部分(包含下订单部分)
            'set_addRoomModalVisible',
            'set_orderModalVisible',
            'set_currentOrderRoom',
            //酒店订单部分

            //酒店优惠部分
            'set_addCouponVisible',
            'set_couponDetail',
            'set_couponDetailVisible',
        ]),
        ...mapActions([
            //酒店信息部分
            'getHotelByManagerId',
            'updateHotelInfo',
            //酒店房间部分

            //酒店订单部分
            'getAllHotelOrders',
            'executeOrder',
            'checkoutOrder',
            //酒店优惠部分
            'getHotelCoupon',
            'deleteHotelCoupon',
        ]),
        //酒店信息部分
        changeRegion(v){

        },
        changeStar(v){

        },
        saveModify() {
            this.form.validateFields((err, values) => {
                if (!err) {
                    const data = {
                        hotelName: this.form.getFieldValue('hotelName'),
                        address: this.form.getFieldValue('address'),
                        bizRegion: this.form.getFieldValue('bizRegion'),
                        hotelStar: this.form.getFieldValue('hotelStar'),
                        description: this.form.getFieldValue('description'),
                        detail: this.form.getFieldValue('detail'),
                        phoneNum: this.form.getFieldValue('phoneNum'),
                    }
                    this.updateHotelInfo(data).then(()=>{
                        this.modify = false
                    })
                }
            });
        },
        modifyInfo() {
            setTimeout(() => {
                this.form.setFieldsValue({
                    'hotelName': this.managerHotelInfo.hotelName,
                    'address': this.managerHotelInfo.address,
                    'bizRegion': this.managerHotelInfo.bizRegion,
                    'hotelStar': this.managerHotelInfo.hotelStar,
                    'description': this.managerHotelInfo.description,
                    'detail': this.managerHotelInfo.detail,
                    'phoneNum': this.managerHotelInfo.phoneNum,
                })
            }, 0)
            this.modify = true
        },
        cancelModify() {
            this.modify = false
        },
        addHotelPhoto(id) {
            this.set_addHotelPhotoVisible(true)
            this.set_photoHotelId(id)
        },
        refresh(data){
            location.reload()
        },

        //酒店房间部分
        addRoom(record) {
            this.set_addRoomModalVisible(true)
        },
        order(record) {
            this.set_currentOrderRoom(record)
            this.set_orderModalVisible(true)
        },

        //酒店订单部分
        executeO(orderId){
            this.executeOrder(orderId)
        },
        async checkout(orderId){
            await this.checkoutOrder(orderId)
            await this.getHotelByManagerId()
        },

        //酒店优惠部分
        addHotelCoupon(){
            this.set_addCouponVisible(true)
        },
        delHotelCoupon(couponId){
            this.deleteHotelCoupon(couponId)
        },
        checkCouponDetail(couponId){
            let coupon
            for(let i in this.couponList){
                if(this.couponList[i].id==couponId){
                    coupon = this.couponList[i]
                    break
                }
            }
            if(coupon){
                this.set_couponDetail(coupon)
                this.set_couponDetailVisible(true)
            }
            else{
                console.log('遍历couponlist失败',coupon)
            }
        }
    }
}
</script>
<style scoped lang="less">
    .manageHotel-wrapper {
        padding: 50px;
        .chart {
            display: flex;
            align-items: center;
            justify-content: space-between;
            margin-top: 20px
        }
    }
</style>
<style lang="less">
    .manageHotel-wrapper {
        .ant-tabs-bar {
            padding-left: 30px
        }
    }
</style>
<style lang="less">

</style>
<style scoped lang="less">
    .hotelDetailCard {
        padding: 20px 50px;
    }
    .hotel-info {
        display: flex;
        align-items: stretch;
        justify-content: flex-start;
        .info{
            padding: 10px 0;
            display: flex;
            flex-direction: column;
            margin-left: 20px;
            .items {
                display: flex;
                align-items: center;
                margin-bottom: 10px;
                .label{
                    margin-right: 10px;
                    font-size: 18px;
                }
                .value {
                    margin-right: 15px
                }
            }
        }
    }
</style>