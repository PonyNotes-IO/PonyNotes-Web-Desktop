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
                <div class="text-group_3 flex-col justify-between">
                    <span class="text_13">{{item['title']}}</span>
                    <span class="text_14">
                        {{ item['desc'] }}
                    </span>
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
            curr:0,
            pwidth:0,
            active:0,
            defImageSrc: require('@/assets/app-1.png'),

        }
    },
    beforeMount() {
        if(this.data.tabs.length) {
            this.pwidth = parseInt(1017/this.data.tabs.length);
        }
    },
    methods:{
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
            background-color: #fff;
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
</style>


<!-- <template>
    <div class="index-pic">
        <van-tabs v-model="active" class="title-tab" @click="tabsClick1">
            <van-tab v-for="(value,idx) in (data.tabs ||[])" :key="idx" class="tabs-title"  :title="value">
                
            </van-tab>
        </van-tabs>
        <van-swipe ref="swipe" class="xm-swipe" :autoplay="5000" :current="curr" indicator-color="white" @change="onSwipeChange" >
            <van-swipe-item v-for="(item,index) in (data.details|| [])" :key="index">
                <div class="text-group_3 flex-col justify-between">
                    <span class="text_13">{{item['title']}}</span>
                    <span class="text_14">
                        {{ item['desc'] }}
                    </span>
                </div>
                <div class="box_5 flex-col">
                    <img :src="item.img ||getImageUrl()" />
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
            curr:0,
            pwidth:0,
            active:0,
            defImageSrc: require('@/assets/app-1.png'),

        }
    },
    beforeMount() {
        if(this.data.tabs.length) {
            this.pwidth = parseInt(1017/this.data.tabs.length);
        }
    },
    methods:{
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
        }
        /deep/ .van-tabs__line {
            width: 120px;
            background-color: #fff;
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
</style> -->