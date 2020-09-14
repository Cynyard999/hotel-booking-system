<template>
  <a-layout style="background:none">
    <a-layout-content>
      <div class="hotelDetailCard">
        <h1>
          {{ currentHotelInfo.title }}
        </h1>
        <div class="hotel-info">
          <a-card style="width: 480px;height: 270px">
            <img
                alt="example"
                :src='url'
                slot="cover"
                width="480px"
                height="270px"
            />
          </a-card>
          <div class="info">
            <div class="items" v-if="currentHotelInfo.hotelName">
              <span class="label">酒店名称：</span>
              <span class="value">{{ currentHotelInfo.hotelName }}</span>
            </div>
            <div class="items" v-if="currentHotelInfo.address">
              <span class="label">地址</span>
              <span class="value">{{ currentHotelInfo.address }}</span>
            </div>
            <div class="items" v-if="currentHotelInfo.hotelStar">
              <span class="label">星级:</span>
              <span class="value">{{getHotelStar(currentHotelInfo.hotelStar)}}</span>
            </div>
            <div class="items" v-if="currentHotelInfo.rate">
              <span class="label">评分:</span>
              <a-rate style="font-size: 15px; margin-bottom: 1px" :value="currentHotelInfo.rate"
                      disabled allowHalf/>
              <span class="value">（{{ currentHotelInfo.evaluatorNum}}个人评过）</span>
            </div>
            <div class="items" v-if="currentHotelInfo.description">
              <span class="label">酒店简介:</span>
              <span class="value">{{ currentHotelInfo.description }}</span>
            </div>
            <div class="favor" v-if="userInfo.userType=='Client'">
              <a-icon key="setting" theme="filled" :style="{ fontSize: '20px' }"
                      v-bind:class="{ 'isFavoredColor' :favored, 'notFavoredColor': !favored}"
                      type="heart" @click.stop="clickButton()"/>
            </div>
          </div>
        </div>
        <a-tabs>
          <a-tab-pane tab="房间信息" key="1">
            <RoomList :rooms="currentHotelInfo.rooms"></RoomList>
          </a-tab-pane>
          <a-tab-pane tab="酒店详情" key="2">
            <p class="word">{{currentHotelInfo.detail}}</p>
          </a-tab-pane>
          <a-tab-pane tab="累计评价" key="3">
            <a-list
                class="comment-list"
                :header="`共${this.evaluations.length}条评价`"
                item-layout="horizontal"
                :data-source="this.evaluations"
                :pagination="pagination"
            >
              <a-list-item slot="renderItem" slot-scope="item">
                <a-comment :author="item.clientName[0]+'**'" :avatar=url2+item.userId+currentTime>
                  <p slot="content">
                    {{ item.evaluation }}
                  </p>
                  <a-tooltip slot="datetime">
                    <span>居住了{{getDuration(item.checkInDate,item.checkOutDate)}}晚</span>
                  </a-tooltip>
                </a-comment>
              </a-list-item>
            </a-list>
          </a-tab-pane>
        </a-tabs>
      </div>
    </a-layout-content>
  </a-layout>
</template>
<script>
  import {mapGetters, mapActions, mapMutations} from 'vuex'
  import RoomList from './components/roomList'
  import moment from "moment";

  export default {
    name: 'hotelDetail',
    components: {
      RoomList,
    },

    data() {
      return {

        url: "https://hotel-booking-system.oss-cn-beijing.aliyuncs.com/" + Number(
            this.$route.params.hotelId) + "?timestamp=" + new Date().getTime(),
        favored: "",
        url2: "https://hotel-booking-system.oss-cn-beijing.aliyuncs.com/user",
        currentTime:"?timestamp=" + new Date().getTime(),
        pagination: {
          onChange: page => {
            console.log(page);
          },
          pageSize: 3,
        },
      }
    },
    computed: {
      ...mapGetters([
        'currentHotelInfo',
        'userInfo',
        'addSuccess',
        'deleteSuccess',
        'evaluations'
      ])
    },
    mounted() {
      this.set_currentHotelId(Number(this.$route.params.hotelId));
      this.getHotelById();
      this.getEvaluations();
      var that = this;
      var a = this.getIsFavored(Number(this.$route.params.hotelId));
      // console.log(a);
      a.then(function (result) {
        that.favored = result;
      })
    },
    beforeRouteUpdate(to, from, next) {
      this.set_currentHotelId(Number(to.params.hotelId));
      this.getHotelById();
      next()
    },
    methods: {
      ...mapMutations([
        'set_currentHotelId',
        'set_favHotel',
      ]),
      ...mapActions([
        'getHotelById',
        'getIsFavored',
        'favorHotel',
        'disfavorHotel',
        'getEvaluations'
      ]),
      getHotelStar(star) {
        if (star == "Three") {
          return "三星级"
        }
        if (star == "Four") {
          return "四星级"
        }
        if (star == "Five") {
          return "五星级"
        }
        return "暂无星级"
      },
      clickButton() {
        this.set_favHotel(Number(this.$route.params.hotelId)); //把将要收藏/删除的酒店id存在store中
        if (this.favored) {
          this.disfavorHotel();
          this.deleteSuccess_()
        } else {
          this.favorHotel();
          this.addSuccess_();
        }
        this.favored = !this.favored
      },
      getDuration(checkIn,checkOut){
        return moment(checkOut).diff(moment(checkIn), 'days');
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
  .hotelDetailCard {
    padding: 50px 50px;
    .hotel-info {
      display: flex;
      align-items: stretch;
      justify-content: flex-start;
      margin-bottom: 30px;

      .info {
        padding: 10px 0;
        display: flex;
        flex-direction: column;
        margin-left: 20px;

        .items {
          display: flex;
          align-items: center;
          margin-bottom: 10px;

          .label {
            margin-right: 10px;
            font-size: 18px;
          }

          .value {
            margin-right: 15px
          }
        }

        .favor {
          .isFavoredColor {
            color: #ff4d4f;
          }

          .notFavoredColor {
            color: #bfbfbf;
          }
        }
      }
    }
    .word{
      margin-left: 20px;
      word-break:break-all;
      width:700px;
      font-size: 16px;
      text-shadow: 0.5px 0.5px 0.5px;
      line-height:40px
    }
    .comment-list{
      min-height: 350px;
      margin-top: -20px;
    }
  }
</style>