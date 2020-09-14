<template>
    <div class="manageUser-wrapper">
        <a-tabs>
            <a-tab-pane tab="用户管理" key="1">
                <div style="width: 100%; text-align: right; margin:20px 0">
<!--                    <a-button type="primary"  size = "large"><a-icon type="search"/> 搜索用户</a-button>-->
                </div>
                <a-table
                    :columns="columns1"
                    :dataSource="clientList"
                    bordered
                >

                    <span slot="actionClient" slot-scope="record">
                        <a-button type="primary" @click = jumpToManageUserInfo(record) >查看用户</a-button>
                        <a-divider type="vertical"></a-divider>
                        <a-popconfirm
                                title="确定想删除该用户吗？"
                                @confirm="deleteUser(record)"
                                okText="确定"
                                cancelText="取消"
                        >
                            <a-button type="danger" >删除用户</a-button>
                        </a-popconfirm>
                    </span>
                </a-table>
            </a-tab-pane>
            <a-tab-pane tab="酒店工作人员管理" key="2">
                <div style="width: 100%; text-align: right; margin:20px 0">
<!--                    <a-button type="primary"  size = "large"><a-icon type="search"/> 搜索用户</a-button>-->

                </div>
                <a-table
                        :columns="columns2"
                        :dataSource="managerList"
                        bordered
                >

                    <span slot="actionManager" slot-scope="record">
                        <a-button type="primary" @click = jumpToManageUserInfo(record)>查看用户</a-button>
                        <a-divider type="vertical"></a-divider>
                        <a-popconfirm
                                title="确定想删除该用户吗？"
                                @confirm="deleteUser(record)"
                                okText="确定"
                                cancelText="取消"
                        >
                            <a-button type="danger" >删除用户</a-button>
                        </a-popconfirm>
                    </span>
                </a-table>
            </a-tab-pane>
            <a-tab-pane tab="网站营销人员管理" key="3">
                <div style="width: 100%; text-align: right; margin:20px 0">
<!--                    <a-button type="primary"  size = "large"><a-icon type="search"/> 搜索用户</a-button>-->
                    <a-divider type="vertical"></a-divider>
                    <a-button  type="primary" size = "large" @click="addSaleMen"><a-icon type="plus" />添加用户</a-button>
                </div>
                <a-table
                        :columns="columns3"
                        :dataSource="saleMenList"
                        bordered
                >


                    <span slot="actionSaleMen" slot-scope="record">
                       <a-button type="primary" @click = jumpToManageUserInfo(record)>查看用户</a-button>
                        <a-divider type="vertical"></a-divider>
                        <a-popconfirm
                                title="确定想删除该用户吗？"
                                @confirm="deleteUser(record)"
                                okText="确定"
                                cancelText="取消"
                        >
                            <a-button type="danger" >删除用户</a-button>
                        </a-popconfirm>
                    </span>
                </a-table>
            </a-tab-pane>
        </a-tabs>
        <AddManagerModal></AddManagerModal>
        <AddSaleMen></AddSaleMen>
    </div>
</template>
<script>
import { mapGetters, mapMutations, mapActions } from 'vuex'
import AddManagerModal from './components/addManagerModel'
import AddSaleMen from './components/addSaleMenModel'
const columns1 = [
    {  
        title: '用户邮箱',
        dataIndex: 'email',
    },
    {  
        title: '用户名',
        dataIndex: 'userName',
    },

    {
        title: '用户手机号',
        dataIndex: 'phoneNumber',
    },
    {
        title: '信用值',
        dataIndex: 'credit',
    },
    {
        title: 'id',
        dataIndex: 'id',
    },
    {
      title: '操作',
      key: 'actionClient',
      scopedSlots: { customRender: 'actionClient' },
    },
  ];
const columns2 = [
    {
        title: '用户邮箱',
        dataIndex: 'email',
    },
    {
        title: '用户名',
        dataIndex: 'userName',
    },

    {
        title: '用户手机号',
        dataIndex: 'phoneNumber',
    },
    {
        title: 'id',
        dataIndex: 'id',
    },

    {
        title: '操作',
        key: 'actionManager',
        scopedSlots: { customRender: 'actionManager' },
    },
];
const columns3 = [
    {
        title: '用户邮箱',
        dataIndex: 'email',
    },
    {
        title: '用户名',
        dataIndex: 'userName',
    },

    {
        title: '用户手机号',
        dataIndex: 'phoneNumber',
    },
    {
        title: 'id',
        dataIndex: 'id',
    },

    {
        title: '操作',
        key: 'actionSaleMen',
        scopedSlots: { customRender: 'actionSaleMen' },
    },
];
export default {
    name: 'manageHotel',
    data(){
        return {
            formLayout: 'horizontal',
            pagination: {},
            columns1,
            columns2,
            columns3,
            data: [],
            form: this.$form.createForm(this, { name: 'manageUser' }),
        }
    },
    components: {
        AddManagerModal,
        AddSaleMen,
    },
    computed: {
        ...mapGetters([
            'addManagerModalVisible',
            'managerList',
            'clientList',
            'saleMenList',
        ])
    },
    mounted() {
      this.getManagerList()
      this.getClientList()
      this.getSaleMenList()
    },
    methods: {
        ...mapActions([
            'getManagerList',
            'getClientList',
            'getSaleMenList',
            'deleteUserById'
        ]),
        ...mapMutations([
            'set_addManagerModelVisible',
            'set_addSaleMenModelVisible',
            'set_checkUserId',
            'set_deleteUserId',
        ]),
        deleteUser(record){
            this.set_deleteUserId(record.id)
            this.deleteUserById()
            location.reload()
        },
        addManager(){
            this.set_addManagerModelVisible(true)
        },
        addSaleMen(){
            this.set_addSaleMenModelVisible(true)

        },
        jumpToManageUserInfo(record){
            this.set_checkUserId(record.id);
            this.$router.push({ name: 'manageUserInfo', params: { userId: record.id} })
        },
    }
}
</script>
<style scoped lang="less">
    .manageUser-wrapper {
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
    .manageUser-wrapper {
        .ant-tabs-bar {
            padding-left: 30px
        }
    }
</style>
<style lang="less">
    
</style>