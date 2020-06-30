<template>
    <div class="adminManageHotel-wrapper">
                <div style="width: 100%; text-align: right; margin:20px 0">
                    <a-button type="primary" @click="adminAddHotel"><a-icon type="plus" />添加酒店</a-button>
                </div>
                <a-table
                        :columns="columns1"
                        :dataSource="hotelList"
                        bordered
                        :scroll="{ x: 1500 ,y:650}"
                        :pagination="false"
                >
                    <span slot="bizRegion" slot-scope="text">
                        <span v-if="text == 'XinJieKou'">新街口</span>
                        <span v-if="text == 'JiangNing'">江宁</span>
                        <span v-if="text == 'ZhuJiangLu'">珠江路</span>
                        <span v-if="text == 'FuZiMiao'">夫子庙</span>
                        <span v-if="text == 'HeXi'">河西</span>
                        <span v-if="text == 'XianLin'">仙林</span>
                        <span v-if="text == 'JiangBei'">江北</span>
                    </span>

                    <span slot="hotelStar" slot-scope="text">
                        <span v-if="text == 'Three'">三星级</span>
                        <span v-if="text == 'Four'">四星级</span>
                        <span v-if="text == 'Five'">五星级</span>
                    </span>

                    <span slot="action" slot-scope="record">
                        <a-button  v-if = 'record.managerId == null' type="primary" size="small" @click="addHotelManager(record)">添加人员</a-button>
                        <a-button v-else type="primary" size="small" disabled   @click="addHotelManager(record)">添加人员</a-button>
                        <a-divider type="vertical"></a-divider>
                        <a-button size = "small" @click="addHotelPhoto(record)">添加图片</a-button>
                        <a-divider type="vertical"></a-divider>
                         <a-popconfirm
                                 title="确定想删除该酒店吗？"
                                 @confirm="deleteHotelById(record)"
                                 okText="确定"
                                 cancelText="取消"
                         >
                            <a-button type="danger" size="small">删除酒店</a-button>
                        </a-popconfirm>
                    </span>
                </a-table>
        <AddHotelModel></AddHotelModel>
        <AddManagerUser></AddManagerUser>
        <AddHotelPhoto @finished = 'refresh($event)'></AddHotelPhoto>

    </div>
</template>

<script>
    import { mapGetters, mapMutations, mapActions } from 'vuex'
    import AddHotelModel from './components/adminAddHotelModel'
    import AddManagerUser from './components/addManagerModel'
    import AddHotelPhoto from './components/addHotelPhoto'


    const columns1 = [
        {
            title: '酒店名',
            dataIndex: 'hotelName',
            width: 220,
        },
        {
            title: '商圈',
            dataIndex: 'bizRegion',
            scopedSlots: { customRender: 'bizRegion' },
            width: 90,
        },
        {
            title: '地址',
            dataIndex: 'address',
        },
        {
            title: '酒店星级',
            dataIndex: 'hotelStar',
            scopedSlots: { customRender: 'hotelStar' },
            width: 90,
        },
        {
            title: '评分',
            dataIndex: 'rate',
            width: 70,
        },
        {
            title: '简介',
            dataIndex: 'description',
            width: 250,
        },
        {
            title: '工作人员id',
            dataIndex: 'managerId',
            width: 120,
        },
        {
            title: '酒店id',
            dataIndex: 'id',
            width: 80,
        },
        {
            title: '操作',
            key: 'action',
            fixed: 'right',
            width: 100,
            scopedSlots: { customRender: 'action' },
        },
    ];

    export default {
        name: 'adminManageHotel',
        data(){
            return {

                formLayout: 'horizontal',
                pagination: {},
                columns1,
                form: this.$form.createForm(this, { name: 'adminManageHotel' },{ name: 'addManagerModel' }),

            }
        },
        components: {
            AddHotelModel,
            AddManagerUser,
            AddHotelPhoto
        },
        computed: {
            ...mapGetters([
                'hotelList',
                'adminAddHotelModelVisible',
                'photoHotelId',

            ]),
        },
        async mounted() {
            await this.getHotelList()
        },
        methods: {
            ...mapMutations([
                'set_adminAddHotelModelVisible',
                'set_addManagerModelVisible',
                'set_addHotelPhotoVisible',
                'set_photoHotelId',
                'set_updateHotelId',
                'set_deleteHotelId'

            ]),
            ...mapActions([
                'getHotelList',
                'deleteHotel'
            ]),
            adminAddHotel() {

                this.set_adminAddHotelModelVisible(true)
            },
            addHotelManager(record){
                this.set_updateHotelId(record.id)
                this.set_addManagerModelVisible(true)

            },
            addHotelPhoto(record) {
                this.set_addHotelPhotoVisible(true)
                // console.log(record.id)
                this.set_photoHotelId(record.id)
                // console.log(this.photoHotelId)
                // console.log(this.$route.path)
            },
            deleteHotelById(record){
                this.set_deleteHotelId(record.id)
                this.deleteHotel()
                location.reload()
            },
            refresh(data){
                location.reload()
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