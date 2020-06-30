<template>
    <div class="header">
        <div class="label">
            <img src="@/assets/logo.svg" class="logo" alt="logo" @click="jumpToHome">
            <span class="title">NJUSE 酒店管理系统</span>
            
        </div>
        <a-menu class="menu" v-model="current" mode="horizontal" theme="light">
            <a-menu-item key="1" @click="jumpToHome" v-if="userInfo.userType=='Client'">
                <a-icon type="home" />首页
            </a-menu-item>
            <a-menu-item key="3" @click="jumpToHotelManager" v-if="userInfo.userType=='HotelManager'">
                 <a-icon type="switcher" />酒店管理
            </a-menu-item>
            <a-menu-item key="4" @click="jumpToManageAccount" v-if="userInfo.userType=='Manager'">
                <a-icon type="user" />账户管理
            </a-menu-item>
            <a-menu-item key="5" @click="jumpToWebSales" v-if="userInfo.userType=='SaleMen'">
                <a-icon type="switcher"/> 网站营销管理
            </a-menu-item>
            <a-menu-item key="6" @click="jumpToFavList" v-if="userInfo.userType=='Client'">
                    <a-icon type="unordered-list"/>收藏夹
            </a-menu-item>
            <a-menu-item key="7" @click="jumpToManageHotel" v-if="userInfo.userType=='Manager'">
                <a-icon type="switcher" />酒店管理
            </a-menu-item>
            <a-menu-item key="2" @click="jumpToUserInfo">
                <a-icon type="user" />个人中心
            </a-menu-item>
        </a-menu>
        <div class="logout">
            <a-dropdown placement="bottomCenter">
                <div class="user">
                    <a-avatar :src=url+userId+currentTime></a-avatar>
                    <span style="font-size: 14px">{{ userInfo.userName }}</span>
                    <a-icon style="margin-left: 3px; font-size: 16px" type="down"></a-icon>
                </div>
                <a-menu slot="overlay">
                <a-menu-item  @click="jumpToHome" v-if="userInfo.userType=='Client'">
                    <a-icon type="home"></a-icon>
                    首页
                </a-menu-item>
                <a-menu-item @click="jumpToUserInfo">
                    <a-icon type="profile"></a-icon>
                    我的信息
                </a-menu-item>
                <a-menu-item @click="quit">
                    <a-icon type="poweroff"></a-icon>
                    退出登录
                </a-menu-item>
                </a-menu>
            </a-dropdown>
        </div>
        
    </div>
    
</template>
<script>
import { mapGetters, mapActions, mapMutations } from 'vuex'
export default {
    name: '',
    data() {
        return {

            url: "https://hotel-booking-system.oss-cn-beijing.aliyuncs.com/user",
            currentTime:"?timestamp=" + new Date().getTime(),
            current: ['0']

        }
    },
    computed: {
        // 使用对象展开运算符将 getter 混入 computed 对象中
        ...mapGetters([
            // 映射 this.userId 为 store.getters.userId
            'userId',
            // 映射 this.userInfo 为 store.getters.userInfo
            'userInfo'
        ])
    },
    created() {
        if (this.$route.name == 'hotelList') {
            this.current = ['1']
        }else if(this.$route.name == 'userInfo') {
            this.current = ['2']
        }else if(this.$route.name == 'manageHotel') {
            this.current = ['3']
        }else if(this.$route.name == 'manageUser'){
            this.current = ['4']
        }else if(this.$route.name == 'manageWebsite'){
            this.current = ['5']
        }else if(this.$route.name == 'favList'){
            this.current = ['6']
        }else if(this.$route.name == 'adminManageHotel'){
            this.current = ['7']
        }
        else{
            this.current = ['0']
        }
    },
    methods: {
        ...mapMutations([

        ]),
        ...mapActions([
            'logout',
            'getHotelByManagerId'
        ]),
        async quit() {
            await this.$store.dispatch('logout')
            this.$router.push('/login')
        },
        jumpToUserInfo() {
            const path = `/user/info/${this.userId}`
            if (this.$route.path !== path) this.$router.push(path)
            this.current = ['2']
        },
        jumpToHome() {
            if(this.userInfo.userType=='Client'){
                const path = `/hotel/hotelList`
                if (this.$route.path !== path) this.$router.push(path)
                this.current = ['1']
            }
            else if(this.userInfo.userType=='HotelManager'){
                this.jumpToHotelManager()
            }
            else if(this.userInfo.userType=='SaleMen'){
                this.jumpToWebSales()
            }
            else if(this.userInfo.userType=='Manager'){
                this.jumpToManageAccount()
            }
            else{
                console.log('this.userInfo.userType异常！为：'+this.userInfo.userType)
                this.current = ['0']
            }

        },
        jumpToHotelManager() {
            this.$router.push({ name: 'manageHotel', params: { managerId: this.userId } })
            this.current = ['3']
        },
        jumpToManageHotel() {
            this.$router.push({ name: 'adminManageHotel'})
            this.current = ['7']
        },
        jumpToManageAccount(){
            this.$router.push({ name: 'manageUser'})
            this.current = ['4']
        },
        jumpToWebSales(){
            this.$router.push({ name: 'manageWebsite'})
            this.current = ['5']
        },
        jumpToFavList(){
            this.$router.push({name:'favList',params: { userId: this.userId }})
            this.current = ['6']
        }
    }
}
</script>
<style scoped lang="less">
    .header {
        display: flex;
        line-height: 44px;
        height: 44px;
        align-items: center;
        justify-content: space-between;
        min-width: 800px;
        .label{
            height: 44px;
            line-height: 44px;
            vertical-align: middle;
            min-width: 400px;

            .logo {
            height: 44px;
            vertical-align: top;
            margin-right: 16px;
            border-style: none;
            cursor: pointer;
          }

          .title {
            font-size: 33px;
            color: rgba(0, 0, 0, .85);
            font-family: Avenir, 'Helvetica Neue', Arial, Helvetica, sans-serif;
            font-weight: 600;
            position: relative;
            top: 2px;
          }

          .search {
              width: 300px; 
              margin-left: 30px
          }
        }
        .menu{
            background: none
        }
        .logout {
            margin-right: 40px;
            .user {
                cursor: pointer;
                display:flex;
                align-items: center;
                span {
                    margin-left: 5px
                }
            }
        }

    }
</style>