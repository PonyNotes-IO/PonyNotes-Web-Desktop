<template>
    <div class="index-bottom">
        <div class="group_4 flex-col xm-center">
            <div class="text-wrapper_3 flex-row">
                <span class="text_9">产品</span>
                <span class="text_10">资源</span>
                <span class="text_11">联系我们</span>
            </div>
            <div class="group_5 flex-row">
                <div class="image-text_1 flex-row justify-between">
                    <img class="image_1" referrerpolicy="no-referrer" src="./img/logo.png" />
                    <span class="text-group_2">小马笔记</span>
                </div>
                <div class="text-wrapper_4 flex-col justify-between">
                    <span class="text_12">桌面端</span>
                    <span class="text_13">手机端</span>
                </div>
                <div class="text-wrapper_5 flex-col justify-between">
                    <span class="text_14">使用教程</span>
                    <span class="text_15">快速上手</span>
                </div>
                <div class="text-wrapper_6 flex-col justify-between">
                    <span class="text_16">B站</span>
                    <span class="text_17">抖音</span>
                </div>
            </div>

            <div class="text-wrapper_7 flex-row">
                <span class="text_18">京ICP备2025131868号</span>
                <span class="text_19">隐私协议</span>
                <span class="text_20">常见问题</span>
                <span class="text_21">小红书</span>
            </div>
            <div class="text-wrapper_8 flex-row justify-between">
                <span class="text_22">用户协议</span>
                <span class="text_23">邮箱</span>
            </div>
        </div>
    </div>
</template>
<script lang="js">
export default {

}
</script>
<style lang="less" scoped>
.index-bottom {
    // width: 1250px;
    margin: 0 auto;
    margin-top: 50px;
    width: 80%;
    @media (max-width:1360px) {
        width: 1250px;
    }
    /* 全局覆盖所有文本元素（p、h1~h6、span 等） */
    p, h1, h2, h3, h4, h5, h6, span, a {
        cursor: pointer !important; /* 强制覆盖默认样式 */
    }

    .group_4 {
        box-shadow: 0px -1px 0px 0px rgba(255, 255, 255, 0.5);
        background-color: rgba(0, 0, 0, 1);
        height: 312px;
        // width: 1680px;

        .text-wrapper_3 {
            width: 585px;
            height: 37px;
            margin: 53px 0 0 660px;

            .text_9 {
                width: 52px;
                height: 37px;
                overflow-wrap: break-word;
                color: rgba(255, 255, 255, 1);
                font-size: 26px;
                font-family: AlibabaPuHuiTi_3_55_Regular;
                font-weight: NaN;
                text-align: left;
                white-space: nowrap;
                line-height: 37px;
            }

            .text_10 {
                width: 52px;
                height: 37px;
                overflow-wrap: break-word;
                color: rgba(255, 255, 255, 1);
                font-size: 26px;
                font-family: AlibabaPuHuiTi_3_55_Regular;
                font-weight: NaN;
                text-align: left;
                white-space: nowrap;
                line-height: 37px;
                margin-left: 233px;
            }

            .text_11 {
                width: 104px;
                height: 37px;
                overflow-wrap: break-word;
                color: rgba(255, 255, 255, 1);
                font-size: 26px;
                font-family: AlibabaPuHuiTi_3_55_Regular;
                font-weight: NaN;
                text-align: left;
                white-space: nowrap;
                line-height: 37px;
                margin-left: 144px;
            }
        }

        .group_5 {
            width: 1156px;
            height: 83px;
            margin: 3px 0 0 20px;

            .image-text_1 {
                width: 294px;
                height: 67px;

                .image_1 {
                    width: 67px;
                    height: 67px;
                }

                .text-group_2 {
                    width: 210px;
                    height: 53px;
                    overflow-wrap: break-word;
                    color: rgba(255, 255, 255, 1);
                    font-size: 52px;
                    font-family: AlimamaFangYuanTiVF-SemiBold-Round;
                    font-weight: NaN;
                    text-align: left;
                    white-space: nowrap;
                    line-height: 63px;
                    margin-top: 8px;
                }
            }

            .text-wrapper_4 {
                width: 54px;
                height: 65px;
                margin: 18px 0 0 345px;

                .text_12 {
                    width: 54px;
                    height: 25px;
                    overflow-wrap: break-word;
                    color: rgba(255, 255, 255, 1);
                    font-size: 18px;
                    font-family: AlibabaPuHuiTi_3_55_Regular;
                    font-weight: NaN;
                    text-align: left;
                    white-space: nowrap;
                    line-height: 25px;
                }

                .text_13 {
                    width: 54px;
                    height: 25px;
                    overflow-wrap: break-word;
                    color: rgba(255, 255, 255, 1);
                    font-size: 18px;
                    font-family: AlibabaPuHuiTi_3_55_Regular;
                    font-weight: NaN;
                    text-align: left;
                    white-space: nowrap;
                    line-height: 25px;
                    margin-top: 15px;
                }
            }

            .text-wrapper_5 {
                width: 71px;
                height: 65px;
                margin: 18px 0 0 230px;

                .text_14 {
                    width: 71px;
                    height: 25px;
                    overflow-wrap: break-word;
                    color: rgba(255, 255, 255, 1);
                    font-size: 18px;
                    font-family: AlibabaPuHuiTi_3_55_Regular;
                    font-weight: NaN;
                    text-align: left;
                    white-space: nowrap;
                    line-height: 25px;
                }

                .text_15 {
                    width: 71px;
                    height: 25px;
                    overflow-wrap: break-word;
                    color: rgba(255, 255, 255, 1);
                    font-size: 18px;
                    font-family: AlibabaPuHuiTi_3_55_Regular;
                    font-weight: NaN;
                    text-align: left;
                    white-space: nowrap;
                    line-height: 25px;
                    margin-top: 15px;
                }
            }

            .text-wrapper_6 {
                width: 36px;
                height: 65px;
                margin: 18px 0 0 126px;

                .text_16 {
                    width: 29px;
                    height: 25px;
                    overflow-wrap: break-word;
                    color: rgba(255, 255, 255, 1);
                    font-size: 18px;
                    font-family: AlibabaPuHuiTi_3_55_Regular;
                    font-weight: NaN;
                    text-align: left;
                    white-space: nowrap;
                    line-height: 25px;
                }

                .text_17 {
                    width: 36px;
                    height: 25px;
                    overflow-wrap: break-word;
                    color: rgba(255, 255, 255, 1);
                    font-size: 18px;
                    font-family: AlibabaPuHuiTi_3_55_Regular;
                    font-weight: NaN;
                    text-align: left;
                    white-space: nowrap;
                    line-height: 25px;
                    margin-top: 15px;
                }
            }
        }

        .text-wrapper_7 {
            width: 1174px;
            height: 30px;
            margin: 11px 0 0 20px;

            .text_18 {
                width: 223px;
                height: 30px;
                overflow-wrap: break-word;
                color: rgba(255, 255, 255, 1);
                font-size: 21px;
                font-family: AlibabaPuHuiTi_3_55_Regular;
                font-weight: NaN;
                text-align: left;
                white-space: nowrap;
                line-height: 30px;
            }

            .text_19 {
                width: 71px;
                height: 25px;
                overflow-wrap: break-word;
                color: rgba(255, 255, 255, 1);
                font-size: 18px;
                font-family: AlibabaPuHuiTi_3_55_Regular;
                font-weight: NaN;
                text-align: left;
                white-space: nowrap;
                line-height: 25px;
                margin: 4px 0 0 416px;
            }

            .text_20 {
                width: 71px;
                height: 25px;
                overflow-wrap: break-word;
                color: rgba(255, 255, 255, 1);
                font-size: 18px;
                font-family: AlibabaPuHuiTi_3_55_Regular;
                font-weight: NaN;
                text-align: left;
                white-space: nowrap;
                line-height: 25px;
                margin: 4px 0 0 213px;
            }

            .text_21 {
                width: 54px;
                height: 25px;
                overflow-wrap: break-word;
                color: rgba(255, 255, 255, 1);
                font-size: 18px;
                font-family: AlibabaPuHuiTi_3_55_Regular;
                font-weight: NaN;
                text-align: left;
                white-space: nowrap;
                line-height: 25px;
                margin: 4px 0 0 126px;
            }
        }

        .text-wrapper_8 {
            width: 517px;
            height: 25px;
            margin: 14px 0 56px 660px;

            .text_22 {
                width: 71px;
                height: 25px;
                overflow-wrap: break-word;
                color: rgba(255, 255, 255, 1);
                font-size: 18px;
                font-family: AlibabaPuHuiTi_3_55_Regular;
                font-weight: NaN;
                text-align: left;
                white-space: nowrap;
                line-height: 25px;
            }

            .text_23 {
                width: 36px;
                height: 25px;
                overflow-wrap: break-word;
                color: rgba(255, 255, 255, 1);
                font-size: 18px;
                font-family: AlibabaPuHuiTi_3_55_Regular;
                font-weight: NaN;
                text-align: left;
                white-space: nowrap;
                line-height: 25px;
            }
        }
    }
}
</style>
