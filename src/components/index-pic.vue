<template>
    <div class="index-pic">
        <div class="text-wrapper_5 flex-row">
            <span v-for="(value,idx) in (data.tabs ||[])" :key="idx" class="tabs-title" @click="changeIdx(idx)">{{ value }}</span>
        </div>
        <div class="box_3 flex-col">
            <div class="box_4 flex-col" :style="{marginLeft:(pwidth*(curr))+'px',minWidth:pwidth+'px'}"></div>
        </div>
        <div class="text-group_3 flex-col justify-between">
            <span class="text_13">{{data.details[curr]['title']}}</span>
            <span class="text_14">
                {{ data.details[curr]['desc'] }}
            </span>
        </div>
        <div class="box_5 flex-col">
            <img :src="getImageUrl()" />
        </div>
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

    .text-group_3 {
        width: 1016px;
        height: 106px;
        margin: 75px 0 0 288px;

        .text_13 {
            width: 173px;
            height: 49px;
            overflow-wrap: break-word;
            color: rgba(255, 255, 255, 1);
            font-size: 35px;
            font-family: AlibabaPuHuiTi_3_65_Medium;
            font-weight: NaN;
            text-align: left;
            white-space: nowrap;
            line-height: 49px;
            margin-left: 421px;
        }

        .text_14 {
            width: 1016px;
            height: 35px;
            overflow-wrap: break-word;
            color: rgba(255, 255, 255, 1);
            font-size: 24px;
            font-family: AlibabaPuHuiTi_3_55_Regular;
            font-weight: NaN;
            text-align: left;
            white-space: nowrap;
            line-height: 35px;
            margin-top: 22px;
        }
    }

    .box_5 {
        border-radius: 17px;
        //   background-image: url(./img/3724626055e249ff8d96ab4311d13520_mergeImage.png);
        width: 1250px;
        height: 818px;
        border: 0.875px solid rgba(151, 151, 151, 1);
        margin: 65px 0 0 171px;
    }
}
</style>