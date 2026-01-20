<template>
    <div class="index-pic">
        <van-tabs v-model="active" class="title-tab" @click="tabsClick1">
            <van-tab v-for="(value,idx) in (data.tabs ||[])" :key="idx" class="tabs-title"  
            :class="{ 'active-tab': active === idx }"
            :title="value">
                
            </van-tab>
        </van-tabs>
        <van-swipe ref="swipe" class="xm-swipe" :autoplay="5000" :current="curr" indicator-color="white" @change="onSwipeChange" >
            <van-swipe-item v-for="(item,index) in (data.details|| [])" :key="index">
                <!-- <div class="text-group_3 flex-col justify-between">
                    <span class="text_13">{{item['title']}}</span>
                    <span class="text_14">
                        {{ item['desc'] }}
                    </span>
                </div> -->
                <div class="text-group_3 flex-col justify-between">
                <span class="text_13">{{ currentTitle }}</span>
                <span class="text_14">{{ currentDesc }}</span>
                </div>

                <div class="box_5 flex-col">
                    <img :src="item.img ||getImageUrl()"  :class="{ 'active-img': active === index }"/>
                </div>
            </van-swipe-item>

        </van-swipe>
    </div>
</template>
<script lang="js">
export default {
    props: {
        data: {
            type: Object,
            required: true
        }
    },
    data() {
        return {
            currentTitle: '',
            currentDesc: '',
            titleIndex: 0,
            descIndex: 0,
            typingTimer: null,

            curr:0,
            pwidth:0,
            active:0,
            defImageSrc: require('@/assets/app-1.png'),

        }
    },
    watch: {
    // 监听active变化，触发文字动画
        active(newVal) {
            this.resetTyping();
            this.startTyping(newVal);
        }
    },
    beforeMount() {
        if(this.data.tabs.length) {
            this.pwidth = parseInt(1017/this.data.tabs.length);
        }
    },
    mounted() {
        this.startTyping(); // 初始加载时开始文字动画
    },
    methods:{
  // 重置文字显示状态
        resetTyping() {
            clearInterval(this.typingTimer);
            this.currentTitle = '';
            this.currentDesc = '';
            this.titleIndex = 0;
            this.descIndex = 0;
        },
        // 逐字显示文字
        startTyping(activeIndex) {
            const currentItem = this.data.details[activeIndex] || {};
            const title = currentItem.title || '';
            const desc = currentItem.desc || '';

            this.typingTimer = setInterval(() => {
            // 逐字显示标题
            if (this.titleIndex < title.length) {
                this.currentTitle += title[this.titleIndex];
                this.titleIndex++;
            }
            // 标题显示完成后开始显示描述
            else if (this.descIndex < desc.length) {
                this.currentDesc += desc[this.descIndex];
                this.descIndex++;
            } else {
                clearInterval(this.typingTimer);
            }
            }, 50); // 每个字的间隔时间（毫秒）
        },

        changeIdx(idx) {
            this.curr = idx;
        },
        onSwipeChange(e) {
            // console.log('onSwipeChange',e);
            this.active = e;

        },
        tabsClick1(index) {
            if(this.$refs.swipe) {
                console.log('inst',this.$refs.swipe)
                this.$refs.swipe.swipeTo(index);
            } else {
                console.log('not find',this.$refs);
            }
        },
        getImageUrl() {
            const url = this.data.details[this.curr]['img'] || '';
            // return "url("+(url || `/assets/app-1.png`)+")";
            return  url || this.defImageSrc;
        }
    }
}
</script>
<style lang="less" scoped>
.index-pic {
    width: 1250px;
    margin: 0 auto;
    margin-top: 50px;
    .title-tab {
        height: 60px;
        /deep/ .van-tabs__wrap {
            height: 60px;
        }
        /deep/ .van-tabs__nav {
            background-color: unset;
        }
        /deep/ .van-tab__text {
            width: 100%;
            text-align: center;
            height: 28px;
            font-size: 20px;
            overflow-wrap: break-word;
            color: rgba(255, 255, 255, 1);
            white-space: nowrap;
            line-height: 28px;
            transition: color 0.3s ease;
        }

        /deep/ .van-tab--active .van-tab__text {
            color: #f89575 !important; /* 当前tab文字变红 */
        }

        /deep/ .van-tabs__line {
            width: 120px;
            // background-color: #fff;
            background-color: #f89575 !important; 
            height: 5px;
            // bottom:18px;
        }
    }

    .xm-swipe {
        text-align: center;
        .text-group_3 {
            text-align: center;
            // width: 1016px;
            // height: 106px;
            margin-top: 75px;
            // margin: 75px 0 0 288px;

            .text_13, .text_14 {
                opacity: 1;
                transition: opacity 0.5s ease-out, transform 1s cubic-bezier(0.1, 0.7, 1, 0.1);
                // transform: translateX(0px);
            }
            
            .show-text {
                opacity: 1;
                transform: translateY(0);
            }
            .text_13 {
                width: 100%;
                text-align: center;
                height: 49px;
                overflow-wrap: break-word;
                color: rgba(255, 255, 255, 1);
                font-size: 35px;
                font-family: AlibabaPuHuiTi_3_65_Medium;
                font-weight: NaN;
                white-space: nowrap;
                line-height: 49px;
                // margin-left: 421px;
                
            }

            .text_14 {
                width: 100%;
                text-align: center;
                height: 35px;
                overflow-wrap: break-word;
                color: rgba(255, 255, 255, 1);
                font-size: 24px;
                font-family: AlibabaPuHuiTi_3_55_Regular;
                font-weight: NaN;
                white-space: nowrap;
                line-height: 35px;
                margin-top: 22px;
                margin-bottom: 20px;
            }
        }

        .box_5 {
            border-radius: 17px;
            //   background-image: url(./img/3724626055e249ff8d96ab4311d13520_mergeImage.png);
            width: 1250px;
            height: 818px;
            border: 0.875px solid rgba(151, 151, 151, 1);
            // margin: 65px 0 0 171px;
            position: relative;
            img {
                position: absolute;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                object-fit: cover;
                opacity: 0;
                transition: opacity 1s ease;
                
                &.active-img {
                    opacity: 1;
                }
            }
        }
    }
    .text-wrapper_5 {
        width: 904px;
        height: 28px;
        display: inline-flex;
        margin: 60px 0 0 336px;
        .tabs-title {
            flex:1;
            cursor: pointer;
            height: 28px;
            overflow-wrap: break-word;
            color: rgba(255, 255, 255, 1);
            font-size: 20px;
            font-family: AlibabaPuHuiTi_3_65_Medium;
            font-weight: NaN;
            text-align: center;
            white-space: nowrap;
            line-height: 28px;
        }
    }

    .box_3 {
        background-color: rgba(59, 59, 59, 1);
        border-radius: 3px;
        height: 6px;
        width: 1017px;
        margin: 13px 0 0 288px;

        .box_4 {
            background-color: rgba(255, 255, 255, 1);
            border-radius: 3px;
            width: 203px;
            height: 6px;
        }
    }
}

.box_5 {
  /* 原有样式保持不变 */
  overflow: hidden; /* 防止图片动画溢出 */

  img {
    /* 原有样式保持不变 */
    opacity: 0;
    // transform: translateY(50px) scale(0.95); /* 初始位置（下方+缩小） */
    // transition: all 0.6s cubic-bezier(0.2, 0.8, 0.2, 1);

    /* 初始位置：右下方（100px右移+100px下移）+ 缩小状态 */
    transform: translate(100px, 100px) scale(0.8);
    /* 动画过渡：所有属性变化在0.8秒内完成，使用ease-out缓动函数 */
    transition: all 1s cubic-bezier(0.16, 1, 0.3, 1);

    &.active-img {
        box-shadow: 
            0 0 0 1px rgb(53 72 91 / 20%), /* 内层细边加深 */
            0 4px 6px rgb(0 0 0 / 8%),    /* 中层阴影扩大 */
            0 10px 15px rgb(0 0 0 / 10%), /* 外层阴影增强 */
            0 30px 40px rgb(0 0 0 / 12%); /* 远距离阴影加深 */

    //   box-shadow:  
    //   0 0 0 1px rgb(53 72 91 / 10%), 
    //   0 2px 2px rgb(0 0 0 / 3%), 
    //   0 4px 4px rgb(0 0 0 / 4%), 
    //   0 10px 8px rgb(0 0 0 / 5%), 
    //   0 15px 15px rgb(0 0 0 / 6%), 
    //   0 30px 30px rgb(0 0 0 / 7%), 
    //   0 70px 65px rgb(0 0 0 / 9%);
      opacity: 1;
      transform: scale(1.05) translateX(0) translateY(0);
      /* 强化跳动动画，增加幅度和时长 */
      animation: bounce 0.8s cubic-bezier(0.2, 0.85, 0.4, 1.275) forwards;
    //   transform: translate(0, 0) scale(1);
    //   transform: translateY(0) scale(1); /* 最终位置 */
    //   animation: bounce 0.6s ease-out 0.3s; /* 延迟触发跳动动画 */
    }
  }
}

// /* 定义跳动动画 */
// @keyframes bounce {
//   0% { transform: translateY(0) scale(1); }
//   50% { transform: translateY(-10px) scale(1.05); } /* 上跳+轻微放大 */
//   100% { transform: translateY(0) scale(1); }
// }

@keyframes bounce {
  0% {
    transform: scale(1.05) translateX(0) translateY(0); /* 刚进入时轻微放大 */
  }
  50% {
    transform: scale(1.1) translateX(-5px) translateY(-15px); /* 向上左弹跳，更大幅度放大 */
  }
  100% {
    transform: scale(1) translateX(0) translateY(0); /* 回落至正常状态 */
  }
}
</style>


