<template>
  <a-card hoverable class="hotelCard ant-col-xs-7 ant-col-lg-5 ant-col-xxl-3">
    <img
      alt="example"
      :src="url"
      slot="cover"
      referrerPolicy="no-referrer"
    />
    <a-tooltip :title="hotel.title" placement="top">
      <a-card-meta :title="hotel.hotelName">
      <template slot="description">
        <a-rate style="font-size: 15px;color: #fa8c16;" :value="hotel.rate" disabled allowHalf/>
        <span class="rate-text">{{numFilter(hotel.rate)}}分</span>
        <p style="margin-top: 10px;margin-bottom: 1px">{{getHotelStar(hotel.hotelStar)}}</p>
      </template>
    </a-card-meta>
    </a-tooltip>

    <template  slot="actions" v-if="userInfo.userType=='Client'">
<!--      <a-icon key="setting" theme="filled" v-bind:class="{ 'isFavoredColor' :true, 'notFavoredColor': !favored}" type="heart" @click.stop="clickButton(hotel.id)"/>-->
      <a-icon key="setting" theme="filled" :class= "favored? 'isFavoredColor' : 'notFavoredColor'" type="heart" @click.stop="clickButton(hotel.id)"/>
    </template>
  </a-card>
</template>
<script>
  import { mapGetters, mapMutations, mapActions } from 'vuex'
export default {
  name: '',
  props: {
    hotel: {},
  },

  data() {
    return {
      favored:false,
      url:"https://hotel-booking-system.oss-cn-beijing.aliyuncs.com/"+ this.hotel.id+"?timestamp=" + new Date().getTime()
    }
  },
  mounted() {
    var that=this;
    var a = this.getIsFavored(this.hotel.id);
    a.then(function (result) {that.favored = result;})
  },

  computed:{
    ...mapGetters([
        'userId',
        'addSuccess',
        'deleteSuccess',
        'userInfo'
    ])
  },
  methods:{
    ...mapActions([
        'favorHotel',
        'disfavorHotel',
        'getIsFavored'
    ]),
    ...mapMutations([
        'set_favHotel'
    ]),
    getHotelStar(star){
      if(star == "Three"){
        return "三星级"
      }
      if(star == "Four"){
        return "四星级"
      }
      if(star == "Five"){
        return "五星级"
      }
      return "暂无星级"
    },
    numFilter(value){
      return parseFloat(value).toFixed(1)
    },
    clickButton(hotelId) {
      this.set_favHotel(hotelId); //把将要收藏/删除的酒店id存在store中
      if (this.favored){
        this.disfavorHotel();
        this.deleteSuccess_()
      }
      else {
        this.favorHotel();
        this.addSuccess_();
      }
      this.favored = !this.favored
    },
    addSuccess_() {
      if (this.addSuccess) {
        this.$notification.open({
          message: '收藏成功',
          duration: 1,
        });
      } else {
        this.$notification.open({
          message: '收藏失败',
          duration: 1,
        });
      }
    },
    deleteSuccess_() {
      if (this.deleteSuccess) {
        this.$notification.open({
          message: '取消收藏成功',
          duration: 1,
        });
      } else {
        this.$notification.open({
          message: '取消收藏失败',
          duration: 1,
        });
      }
    },
  }
}
</script>
<style scoped lang="less">
    .hotelCard {
        margin: 20px 40px;
        min-width: 320px;
        max-height: 450px;
        img {
          height: 300px;
        }
      .isFavoredColor{
        color: #ff4d4f;
      }
      .notFavoredColor{
        color:#bfbfbf;
      }
    }
</style>

<style lang="less">
.hotelCard{

  .ant-card-body{
    padding: 12px
  }
  .rate-text{
    color: #fa8c16;
    font-size: 13px;
    position: relative;
    bottom:-1px;
  }
  .ant-card-actions{
    background: rgba(0,0,0,0.02)
  }
}

.ant-card-hoverable:hover {
  box-shadow: 0 2px 8px rgba(0,0,0,0.4)
}

</style>