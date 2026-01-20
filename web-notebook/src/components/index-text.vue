<template>
    <div class="index-text">
        <div class="text-wrapper_4">
            <span class="text_6">{{ show }}</span>
        </div>
    </div>
</template>
<script lang="js">
export default {
    props: {
        text: {
            type: String,
            required: true,
        }
    },
    data() {
        return {
            show: '',
            idx: 0,
            intervalId:undefined,
        }
    },
    mounted() {
        this.print();
    },
    methods: {
        print() {
            if(!this.show) {
                setTimeout(() => {
                    this.startPrint();
                }, 500);
            }
        },
        startPrint() {
            this.intervalId = setInterval(() => {
                if(this.idx < this.text.length) {
                    this.show += this.text[this.idx];
                    if(this.idx == this.text.length -1) {
                        clearInterval(this.intervalId);
                        this.idx = -1;
                        setTimeout(() => {
                            this.show = '';
                            this.startPrint();
                        }, 3000);
                    }
                }
                this.idx++;
            }, 200);
        }

    }

}
</script>
<style lang="less" scoped>
.index-text {
    /* 全局覆盖所有文本元素（p、h1~h6、span 等） */
    p, h1, h2, h3, h4, h5, h6, span, a {
        cursor: pointer !important; /* 强制覆盖默认样式 */
    }
    width: 100%;
    .text-wrapper_4 {
      height: 54px;
      margin: 0 auto;
      margin-top: 135px;
      overflow-wrap: break-word;
      font-size: 0;
      font-family: DingTalk-JinBuTi;
      font-weight: NaN;
      text-align: center;
      white-space: nowrap;
      line-height: 54px;
    //   margin: 135px 0 0 594px;
      .text_6 {
        width: 405px;
        height: 54px;
        overflow-wrap: break-word;
        color: rgba(255, 255, 255, 1);
        font-size: 45px;
        font-family: DingTalk-JinBuTi;
        font-weight: NaN;
        text-align: left;
        white-space: nowrap;
        line-height: 54px;
      }
      .text_7 {
        width: 405px;
        height: 54px;
        overflow-wrap: break-word;
        color: rgba(255, 255, 255, 1);
        font-size: 45px;
        font-family: DingTalk-JinBuTi;
        font-weight: NaN;
        text-align: left;
        white-space: nowrap;
        line-height: 54px;
      }
    }
}
</style>
