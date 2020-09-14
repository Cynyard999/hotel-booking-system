<template>
    <div class="info-wrapper">
        <a-tabs>
            <a-tab-pane tab="我的信息" key="1">
                <a-form :form="form" style="margin-top: 30px">

                    <a-form-item label="我的头像"  :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }">
                        <a-avatar shape="square" :size="64" :src=url+userId+currentTime></a-avatar>
                    </a-form-item>
                    <a-form-item label="用户名" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1  }">
                        <a-input
                            placeholder="请填写用户名"
                            v-decorator="['userName', { rules: [{ required: true, message: '请输入用户名' }] }]"
                            v-if="modify"
                        />
                        <span v-else>{{ userInfo.userName }}</span>
                    </a-form-item>
                    <a-form-item label="邮箱" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }">
                        <span>{{ userInfo.email }}</span>
                    </a-form-item>
                    
                    <a-form-item label="手机号" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }">
                        <a-input
                            placeholder="请填写手机号"
                            v-decorator="['phoneNumber', { rules: [{ required: true, message: '请输入手机号' }] }]"
                            v-if="modify"
                        />
                        <span v-else>{{ userInfo.phoneNumber}}</span>
                    </a-form-item>
                    <a-form-item label="信用值" v-if="userInfo.userType=='Client'" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }">
                        <span>{{ userInfo.credit }}</span>
                    </a-form-item>
                    <a-form-item label="密码" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }" v-if="modify">
                        <a-input
                            type = "password"
                            placeholder="请输入新密码"
                            v-decorator="['password', { rules: [{ required: true, message: '请输入新密码' }] }]"
                        />
                    </a-form-item>
                    <a-form-item  label="确认密码" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }" v-if="modify">
                        <a-input
                                type="password"
                                placeholder="请确认密码"
                                v-decorator="[
                        'confirmPassword',
                        { rules: [{required: true, message: '请输入密码', },{ validator: this.handlePasswordCheck }] }
                    ]"
                        />
                    </a-form-item >
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
                         <a-divider type="vertical"></a-divider>
                         <a-button type="primary" @click="uploadPhoto">
                             上传头像
                         </a-button>
                    </a-form-item>
                </a-form>
            </a-tab-pane>
            <a-tab-pane tab="我的订单" key="2" v-if="userInfo.userType=='Client'">
                <a-table
                    :columns="columns"
                    :dataSource="userOrderList"
                    bordered
                >
                    <span slot="price" slot-scope="text">
                        <span>￥{{ text }}</span>
                    </span>
                    <span slot="roomType" slot-scope="text">
                        <span v-if="text == 'BigBed'">大床房</span>
                        <span v-if="text == 'DoubleBed'">双床房</span>
                        <span v-if="text == 'Family'">家庭房</span>
                    </span>
                    <a-tag slot="orderState" color="blue" slot-scope="text">
                        {{ text }}
                    </a-tag>
                    <span slot="action" slot-scope="record">
                        <a-button type="primary" size="small" @click="checkOrderDetail(record.id)">查看</a-button>
                        <a-divider type="vertical" v-if="record.orderState == '已预订'"></a-divider>
                        <a-popconfirm
                            title="你确定撤销该笔订单吗？"
                            @confirm="confirmCancelOrder(record.id)"
                            @cancel="cancelCancelOrder"
                            okText="确定"
                            cancelText="取消"
                            v-if="record.orderState == '已预订'"
                        >
                            <a-button type="danger" size="small">撤销</a-button>
                        </a-popconfirm>
                        <a-divider type="vertical" v-if="record.orderState == '已完成'"></a-divider>
                        <a-button type="primary" size="small" @click="evaluate(record.id,record.hotelName,record.hotelId)"
                                  v-if="record.orderState == '已完成' && record.evaluation == ''">评价</a-button>
                        <a-popover title="评价内容" trigger="hover" v-if="record.evaluation != ''">
                          <template slot="content">
                            <p>{{record.evaluation}}</p>
                          </template>
                          <a-button type="primary" size="small">
                            已评价
                          </a-button>
                        </a-popover>
                        <a-divider type="vertical" v-if="record.orderState=='异常'"></a-divider>
                        <a-button type="danger" size="small" @click="appeal(record.id)"
                        v-if="record.orderState == '异常'">申诉</a-button>
                    </span>
                </a-table>
            </a-tab-pane>
            <a-tab-pane tab="我的会员" key = "3" v-if="userInfo.userType=='Client'">
                <div v-if="creditQualified==false" style="font-size: large">
                    <p>信用值达到200后才能注册或者查看会员信息哦!</p>
                </div>
                <a-form :form="vipForm" style="margin-top: 30px;width: 30%" v-if="creditQualified">
                    <a-form-item label="会员类型" v-if="creditQualified">
                        <a-select
                            v-decorator="['vipType',{ rules: [{ required: true, message: '请选择会员类别' }] }]"
                            @change="changeType"
                            v-if="isVip == false"
                        >
                            <a-select-option value="1">个人会员</a-select-option>
                            <a-select-option value="2">企业会员</a-select-option>
                        </a-select>
                        <span v-if="vipInfo.vipType == 1&&isVip">个人会员</span>
                        <span v-if="vipInfo.vipType == 2&&isVip">企业会员</span>
                    </a-form-item>
                    <a-form-item v-if="vipType == 2||vipInfo.vipType == 2" label="企业名称">
                        <a-input
                            placeholder="请填写企业名称"
                            v-decorator="['companyName', { rules: [{ required: true, message: '请输入企业名称' }] }]"
                            v-if="isVip == false"
                        />
                        <span v-else>{{ vipInfo.companyName}}</span>
                    </a-form-item>
                    <p v-if="vipType == 2||vipInfo.vipType == 2">注意：企业会员在预定时只能选择企业优惠</p>
                    <a-form-item v-if="vipType == 1||vipInfo.vipType == 1" label="生日" >
                        <a-date-picker
                            placeholder="请选择日期"
                            v-decorator="['birthday', {rules: [{ required: true, message: '请输入日期' }] }]"
                            v-if="isVip == false"
                        />
                        <span v-else>{{vipInfo.birthday}}</span>
                    </a-form-item>
                    <a-form-item :wrapper-col="{ span: 12, offset: 5 }" v-if="vipType&&!isVip">
                        <a-button type="primary" @click="showConfirm">
                            确定注册
                        </a-button>
                    </a-form-item>
                </a-form>
            </a-tab-pane>
        </a-tabs>
        <OrderDetailModal></OrderDetailModal>
        <Evaluate></Evaluate>
        <AddHotelPhoto @finished = 'refresh($event)'></AddHotelPhoto>
        <a-modal v-model="confirmVisible" title="注册提示" @ok="register">
            <p>注册后会员信息将不能修改，确定要注册会员吗？</p>
        </a-modal>
    </div>
</template>
<script>
import { mapGetters, mapMutations, mapActions } from 'vuex'
import OrderDetailModal from "./components/orderDetailModal";
import AddHotelPhoto from "../admin/components/addHotelPhoto";
import Evaluate from "./components/makeEvaluation"
const columns = [
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
        title: '房价',
        dataIndex: 'price',
    },
    {
        title: '状态',
        filters: [{ text: '已预订', value: '已预订' }, { text: '已撤销', value: '已撤销' }, { text: '已入住', value: '已入住' },
            {text: '已入住',value:'已入住'},{text:'已完成',value:'已完成'},{text:'已申诉',value:'已申诉'}],
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
export default {
    name: 'info',
    data(){
        return {
            modify: false,
            vipType:0,
            creditQualified:false,
            isVip:false,
            confirmVisible: false,
            formLayout: 'horizontal',
            pagination: {},
            columns,
            data: [],
            form: this.$form.createForm(this, { name: 'coordinated' }),
            vipForm:this.$form.createForm(this, { name: 'coordinated2' }),
            url: "https://hotel-booking-system.oss-cn-beijing.aliyuncs.com/user",
            currentTime:"?timestamp=" + new Date().getTime(),
        }
    },
    components: {
        OrderDetailModal,
        AddHotelPhoto,
        Evaluate
    },
    computed: {
        ...mapGetters([
            'userId',
            'userInfo',
            'userOrderList',
            'vipInfo'
        ])
    },
    async mounted() {
        await this.getUserInfo()
        await this.getUserOrders()
        await this.getVipInfo()
        if (this.userInfo.credit>=200){
            this.creditQualified = true
        }
        if (this.vipInfo.vipType){
            this.isVip = true
        }
    },
    methods: {
        ...mapActions([
            'getUserInfo',
            'getUserOrders',
            'updateUserInfo',
            'cancelOrder',
            'appealOrder',
            'registerVip',
            'getVipInfo',
            'appealOrder',
            'getOrderById',
        ]),
        ...mapMutations([
            'set_vipInfo',
            'set_userOrderDetailVisible',
            'set_makeEvaluationVisible',
            'set_orderId',
            'set_addHotelPhotoVisible',
            'set_photoHotelId',
            'set_evaluatedHotelName',
            'set_evaluatedHotelId'
        ]),
        showConfirm() {
            this.confirmVisible = true;
        },
        register(){
            this.confirmVisible =false;
            this.vipForm.validateFields((err, values) => {
                if (!err&&this.vipForm.getFieldValue('vipType')==1) {
                    const data = {
                        vipType: this.vipForm.getFieldValue('vipType'),
                        birthday: this.vipForm.getFieldValue('birthday').format("YYYY-MM-DD"),
                    }
                    this.registerVip(data).then(()=>{
                        this.isVip = true;
                        this.set_vipInfo(data);
                    })
                }
                else if (!err) {
                    const data = {
                        vipType: this.vipForm.getFieldValue('vipType'),
                        companyName: this.vipForm.getFieldValue('companyName')
                    }
                    this.registerVip(data).then(()=>{
                        this.isVip = true;
                        this.set_vipInfo(data)
                    })
                }
            });
        },
        saveModify() {
            this.form.validateFields((err, values) => {
                if (!err) {
                    const data = {
                        userName: this.form.getFieldValue('userName'),
                        phoneNumber: this.form.getFieldValue('phoneNumber'),
                        password: this.form.getFieldValue('password')
                    }
                    this.updateUserInfo(data).then(()=>{
                        this.modify = false
                    })
                }
            });
        },
        modifyInfo() {
            setTimeout(() => {
                this.form.setFieldsValue({
                    'userName': this.userInfo.userName,
                    'phoneNumber': this.userInfo.phoneNumber,
                })
            }, 0)
            this.modify = true
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
        refresh(data){
            location.reload()
        },
        uploadPhoto(){
            var path = "user"+this.userId
            this.set_addHotelPhotoVisible(true)
            this.set_photoHotelId(path)

        },
        cancelModify() {
            this.modify = false
        },
        confirmCancelOrder(orderId){
            this.cancelOrder(orderId)
        },
        cancelCancelOrder() {

        },
        appeal(orderId){
            this.appealOrder(orderId)
        },
        evaluate(orderId,hotelName,hotelId){
            this.set_orderId(orderId)
            this.set_evaluatedHotelName(hotelName)
            this.set_evaluatedHotelId(hotelId)
            this.set_makeEvaluationVisible(true)
        },
        showEvaluation(evaluation){

        },
        checkOrderDetail(orderId){
            this.set_orderId(orderId)
            this.getOrderById()
            this.set_userOrderDetailVisible(true)
        },
        changeType(v){
            this.vipType = v
        }
    }
}
</script>
<style scoped lang="less">
    .info-wrapper {
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
    .info-wrapper {
        .ant-tabs-bar {
            padding-left: 30px
        }
    }
</style>
<style lang="less">
    
</style>