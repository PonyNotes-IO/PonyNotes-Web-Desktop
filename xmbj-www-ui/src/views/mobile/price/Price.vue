<template>
  <div class="page flex-col">
    <div class="block_2 flex-col">
      <MobileNavBar 
        :show-logo="true"
        :show-title="true"
        :show-login="true"
        :show-register="true"
        :show-more="true"
      />
      <div class="text-wrapper_2">
        <span class="text_5">让</span>
        <span class="text_6">笔记</span>
        <span class="text_7"></span>
        <span class="paragraph_1">
          更简单
          <br />
          让知识&nbsp;更智慧
        </span>
      </div>
      <span class="paragraph_2">
        开源、本地优先、云同步
        <br />
        多模态记录与AI&nbsp;并存
        <br />
        助你高效构建个人与团队的知识系统
      </span>
      <div class="section_2 flex-col"></div>
    </div>
    <img
      class="image_2"
      referrerpolicy="no-referrer"
      src="./assets/img/image.png"
    />
    <div class="block_3 flex-col">
      <button class="button_1 flex-col" @click="onClick_1"><span class="text_9">免费下载使用</span></button>
    </div>
    <div class="block_4 flex-col">
      <div class="tabs_1 flex-col">
        <div class="text-wrapper_3 flex-row justify-between">
          <span :class="payType == 1? 'text_10':'text_11'" @click="changePayType(1)">月付</span>
          <span  :class="payType == 2? 'text_10':'text_11'" @click="changePayType(2)">年付</span>
        </div>
        <div class="section_3 flex-row justify-between">
          <div :class="payType == 1? 'box_3 flex-col':'box_4 flex-col'" @click="changePayType(1)"></div>
          <div :class="payType == 2? 'box_3 flex-col':'box_4 flex-col'" @click="changePayType(2)"></div>
        </div>
      </div>
      <span class="text_12">年付方案最高可节省20%</span>
      
      <div class="price-cards flex-row">
        <div class="price-card flex-col"
            v-for="(item, index) in PriceTypeLoopData"
            :key="index">
            <div v-if ="item.slot1 === 1" class="group_5 flex-col justify-between" >
              <div class="text-wrapper_4 flex-row">
                <span  class="text_15">{{ payType === 1 ? item.price_title_month : item.price_title_year }}</span>
                <span class="text_16">{{ payType === 1 ? item.price_subtitle_month : item.price_subtitle_month }}</span>
                <span class="text_17">{{ payType === 1 ? item.price_value_month : item.price_value_year }}</span>
              </div> 
              <div class="box_5 flex-row" >
                <div
                  class="image_4"
                  referrerpolicy="no-referrer"
                > </div>
                <span class="paragraph_4">
                  {{ payType === 1 ? item.price_content_month : item.price_content_year }}
                </span>
              </div>
            </div>
            <div v-if ="item.slot1 === 2" class="group_5 flex-col">
              <div class="text-wrapper_4 flex-row">
                <span class="text_15">{{ payType === 1 ? item.price_title_month : item.price_title_year }}</span>
                <span class="text_16">{{ payType === 1 ? item.price_subtitle_month : item.price_subtitle_month }}</span>
                <!-- <span  class="paragraph_3"> {{ payType === 1 ? item.price_content_month : item.price_content_year }}</span> -->
                <span class="text_17">{{ payType === 1 ? item.price_value_month : item.price_value_year }}</span>
              </div>
              <div class="box_5 flex-row" >
                <div
                  class="image_4"
                  referrerpolicy="no-referrer"
                > </div>
                <span class="paragraph_4">
                  {{ payType === 1 ? item.price_content_month : item.price_content_year }}
                </span>
              </div>
            </div>
            <div  v-if ="item.slot1 === 3"  class="group_5 flex-col">
              <div class="text-wrapper_4 flex-row">
                <span class="text_15">{{ payType === 1 ? item.price_title_month : item.price_title_year }}</span>
                <span class="text_16">{{ payType === 1 ? item.price_subtitle_month : item.price_subtitle_year }}</span>
                <span class="text_17">{{ payType === 1 ? item.price_value_month : item.price_value_year }}</span>
              </div>
              <div class="box_5 flex-row">
                <div
                  class="image_4"
                  referrerpolicy="no-referrer"
                > </div>
                <span class="paragraph_4">
                  {{ payType === 1 ? item.price_content_month : item.price_content_year }}
                </span>
              </div>
            </div>
            <div v-if ="item.slot1 === 4" class="group_5 flex-col">
              <div class="text-wrapper_4 flex-row">
                <span class="text_15">{{ payType === 1 ? item.price_title_month : item.price_title_year }}</span>
                <span class="text_16">{{ payType === 1 ? item.price_subtitle_month : item.price_subtitle_year }}</span>
                <span class="text_17">{{ payType === 1 ? item.price_value_month : item.price_value_year }}</span>
              </div>
              <div class="box_5 flex-row">
                <div
                  class="image_4"
                  referrerpolicy="no-referrer"
                > </div>
                <span class="paragraph_4">
                  {{ payType === 1 ? item.price_content_month : item.price_content_year }}
                </span>
              </div>
            </div>
        </div>
      </div>
      <span class="text_24">方案与功能</span>
      <div  class="price-feature-card flex-col" v-for="(item, index) in loopFeatureData" :key="index">
        <div class="price-feature flex-row justify-between">
       
          <div class="text-group_1 flex-col">
            <span class="text_25">{{item.title}}</span>
            <div  class="price-feature-card flex-row" v-for="(item_feature, content_index) in item.contents" :key="content_index">
              <span class="text_26 flex-col">{{item_feature.content}}</span>
              <img class="thumbnail_5 flex-col"
                referrerpolicy="no-referrer"
                src="./assets/img/icon_feature.png"
              />
            </div>
          </div>

        </div>
      </div>
    </div>
    <div class="block_6 flex-col">
      <span class="text_46">常见问题</span>
      <div class="text-wrapper_11">
        <span class="text_47">1、我需要更多的云存储空间怎么办？</span>
        <span class="paragraph"><br /></span>
        <span class="paragraph">
          你可以随时通过「账户设置&nbsp;→&nbsp;存储扩容」
          <br />
          购买额外空间。我们提供：
          <br />
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;•&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;5GB&nbsp;¥5/月
          <br />
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;•&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;20GB&nbsp;¥15/月
          <br />
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;•&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;50GB&nbsp;¥35/月
        </span>
        <span class="paragraph"><br /></span>
        <span class="text_48">空间立即生效，叠加原有容量，无需等待。</span>
      </div>
      <div class="text-wrapper_11">
        <span class="text_47">
          2、AI&nbsp;对话/图片额度用完了怎么办？
          <br />
        </span>
        <span class="paragraph">
          你可以购买&nbsp;AI&nbsp;增值包扩容：
          <br />
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;•&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;¥10&nbsp;→&nbsp;100&nbsp;次&nbsp;+&nbsp;10&nbsp;张图像
          <br />
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;•&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;¥30&nbsp;→&nbsp;400&nbsp;次&nbsp;+&nbsp;20&nbsp;张图像
          <br />
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;•&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;¥50&nbsp;→&nbsp;1000&nbsp;次&nbsp;+&nbsp;50&nbsp;张图像
        </span>
        <span class="paragraph">
          <br />
          额度实时到账，不限使用期限。
        </span>
      </div>
      <div class="text-wrapper_11">
        <span class="text_47">
          3、可以随时升级或降级套餐吗？
          <br />
        </span>
        <span class="paragraph">
          您可以随时升级套餐，但当前暂不支持主动降级。
          <br />
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;•&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          升级：支持即时生效，系统将按比例扣除剩余费用，补差价即可完成升级，包含更大云存储空间、更高
          AI额度等权益；
          <br />
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;•&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          降级：为保障数据完整性与使用连续性，当前暂不支持自动降级，避免因降级导致云端数据超额或功能
          失效。
        </span>
      </div>
      <div class="text-wrapper_11">
        <span class="text_47">
          4、学生版只有学生可以买吗？
          <br />
        </span>
        <span class="paragraph">
          不是的，所有用户都可以购买学生版，无需学生认证。
        
          我们推出学生版的初衷是为了提供一个低门槛的入门

          体验，帮助更多用户了解和使用这款&nbsp;AI&nbsp;驱动的笔记

          工具。学生版价格实惠，功能完整，适合：
          <br />
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;•&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;想体验我们笔记生态的用户
          <br />
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;•&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;内容不多、存储需求轻的轻度用户
          <br />
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;•&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;希望尝试&nbsp;AI&nbsp;对话、图像生成功能的新用户
        </span>
        <span class="paragraph">
          <br />
          无论你是学生、上班族，还是内容创作者，只要你喜欢
          这款笔记都可以放心选择学生版开始使用！
        </span>
      </div>
      <div class="text-wrapper_11">
        <span class="text_47">
          5、我的数据会因未续费而丢失吗？
          <br />
        </span>
        <span class="paragraph">
          不会。即使你取消了订阅，数据仍会保留在本地和云端，
         
          你仍可以访问、导出、备份数据。但部分功能（如AI、
         
          协作、云同步）将暂停使用，直到恢复订阅或扩容。

          超过&nbsp;30&nbsp;天未续费的账号，其云端数据可能会被清理，
       
          请及时备份或续费以避免数据丢失。
        </span>
      </div>
      <div class="text-wrapper_11">
        <span class="text_47">
          6、付费订阅后是否支持退款？
          <br />
        </span>
        <span class="paragraph">
          目前付费订阅暂不支持退款，请在购买前充分了解产品
     
          功能和订阅内容。
         
          我们建议用户在订阅前通过免费版充分体验基础功能，
       
          也可以选择价格更低的学生版进行试用。
       
          如果你在使用过程中遇到问题，我们的客服团队会尽力
       
          协助你解决。
          <br />
        </span>
        <span class="paragraph">
          温馨提示：AI调用额度和存储空间属于即时可用型资源
          <br />
          启用后将产生成本，因此暂不支持退款。
        </span>
      </div>
      <div class="text-wrapper_11">
        <span class="text_47">
          7、可以通过第三方工具（如百度网盘、坚果云）
          同步或备份数据吗？
          <br />
        </span>
        <span class="paragraph">
          我们不建议使用百度网盘、坚果云等第三方同步工具对
         
          笔记数据文件夹进行直接同步。
          
          这类工具并非为数据库或实时笔记文件设计，可能导致
          
          如下风险：
          <br />
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;•&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;数据冲突：多端不同步可能造成笔记内容丢失或
  
          版本错误；
          <br />
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;•&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;文件锁定异常：第三方同步程序可能在后台锁定
   
          笔记数据库，影响正常使用；
          <br />
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;•&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;数据损坏：同步未完成或中断时，可能导致笔记

          数据库损坏，无法恢复。
          <br />
          官方推荐的同步方式：
          <br />
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;•&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;使用软件内置的云同步机制（包括标准版和团队

          版的云端空间），数据实时加密同步，稳定安全；
          <br />
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;•&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;如需长期备份，建议使用内置的「一键导出」功

          能，导出为&nbsp;Markdown/PDF等开放格式，再存入网盘

          保存。
        </span>
        <span class="paragraph">
          <br />
          为保障笔记完整性与稳定性，请尽量避免使用第
          <br />
          三方同步软件直接操作笔记存储路径。
        </span>
      </div>
      <div class="text-wrapper_11">
        <span class="text_47">
          8、我在电脑端购买了订阅，如何在&nbsp;iPad&nbsp;等设
          <br />
          备上恢复使用？
          <br />
        </span>
        <span class="paragraph">
          只需使用相同账号登录，系统将自动同步您的订阅权益。
          <br />
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;•&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;无论您通过&nbsp;官网、桌面端&nbsp;还是&nbsp;App&nbsp;端&nbsp;完成购
          <br />
          买，只要登录的是同一账户，订阅状态将在
          <br />
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;各端同步；
          <br />
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;•&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;如果未自动恢复，请前往&nbsp;「账户设置&nbsp;→&nbsp;恢复购
          <br />
          买」&nbsp;尝试手动同步；
          <br />
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;•&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;若仍未恢复，请确认&nbsp;iPad&nbsp;登录账号与购买账号
          <br />
          一致，或联系客户支持协助处理。
          <br />
        </span>
        <span class="paragraph_29">
          温馨提示：Apple&nbsp;内购购买的订阅需通过&nbsp;App&nbsp;Store&nbsp;
          <br />
          登录同一&nbsp;Apple&nbsp;ID&nbsp;并点击「恢复购买」才能生效。
        </span>
      </div>
    </div>
    <div class="block_7 flex-col">
      <img
        class="image_7"
        referrerpolicy="no-referrer"
        src="./assets/img/SketchPnge6d918b7b08b0c49f7bbe81f62c278ef96fd1a7eef15d897c2004a8a671ec00c.png"
      />

       <MobileBottom />
    </div>
   
  </div>
</template>
<script>
export default {
  data() {
    return {
      payType: 1,
      PriceTypeLoopData: [
            // 1. 免费版（月付 + 年付）
            {
              Bg0: require('./assets/img/price_type_bg.png'), // 背景图（需确保路径正确）
              price_title_month: '免费版',   // 月付标题
              price_subtitle_month: '         ',      // 月付副标题（无则空）
              price_content_month: '本地免费使用，你的随身知识库系统，AI图文生成 + 云存储，开启智能学习体验', // 月付内容
              price_value_month: '0元/月/人', // 月付价格
              price_title_year: '免费版',    // 年付标题
              price_subtitle_year: '         ',       // 年付副标题（无则空）
              price_content_year: '本地免费使用，你的随身知识库系统，AI图文生成 + 云存储，开启智能学习体验', // 年付内容
              price_value_year: '0元/年/人',  // 年付价格
              slot1: 1, // 插槽标识（需与 UI 布局对应，可按需调整）
              slot2: 1,
            },
            // 2. 学生版（月付 + 年付）
            {
              Bg0: require('./assets/img/price_type_bg.png'),
              price_title_month: '学生版',   // 月付标题
              price_subtitle_month:'每月2G存储空间', // 月付副标题（突出存储权益）
              price_content_month: '轻装上阵，AI助学从学生版开始小投入大升级学习效率跃升一步！AI图文生成 + 云存储开启智能学习体验', // 月付内容（修正“A1”为“AI”）
              price_value_month: '0元/月/人', // 月付价格（学生版月付免费，年付付费）
              price_title_year: '学生版',    // 年付标题
              price_subtitle_year: '每月2G存储空间',       // 年付副标题（无则空）
              price_content_year: '轻装上阵，AI助学从学生版开始小投入大升级学习效率跃升一步！AI图文生成 + 云存储开启智能学习体验', // 年付内容
              price_value_year: '30元/年/人',  // 年付价格（参考 UI）
              slot1: 2,
              slot2: 2,
            },
            // 3. 标准版（月付 + 年付）
            {
              Bg0: require('./assets/img/price_type_bg.png'),
              price_title_month: '标准版',   // 月付标题
              price_subtitle_month:'每月10G存储空间', // 月付副标题（突出存储权益）
              price_content_month: '向内容创作者与高效工作者的智能笔记空间丰富AI+10GB云同步，助你随时记录、随处创作一站整合写作、整理、归档，让你的笔记存的下', // 月付内容
              price_value_month: '80元/月/人', // 月付价格
              price_title_year: '标准版',    // 年付标题
              price_subtitle_year: '每月10G存储空间',       // 年付副标题（无则空）
              price_content_year: '向内容创作者与高效工作者的智能笔记空间丰富AI+10GB云同步，助你随时记录、随处创作一站整合写作、整理、归档，让你的笔记存的下', // 年付内容
              price_value_year: '80元/年/人',  // 年付价格
              slot1: 3,
              slot2: 3,
            },
            // 4. 团队版（月付 + 年付）
            {
              Bg0: require('./assets/img/price_type_bg.png'),
              price_title_month: '团队版',   // 月付标题
              price_subtitle_month:'每月10G存储空间', // 月付副标题（突出存储权益）
              price_content_month: '团队协作不止是共享，更是高效共创专属权限+共享工作区+大容量云空间轻松协同办公，配套AI能力为团队注入内容生产与决策的智能引擎', // 月付内容
              price_value_month: '180元/月/人', // 月付价格
              price_title_year: '团队版',    // 年付标题
              price_subtitle_year: '每月10G存储空间',       // 年付副标题（无则空）
              price_content_year: '团队协作不止是共享，更是高效共创专属权限+共享工作区+大容量云空间轻松协同办公，配套AI能力为团队注入内容生产与决策的智能引擎', // 年付内容
              price_value_year: '180元/年/人',  // 年付价格
              slot1: 4,
              slot2: 4,
            },
        ],
        loopFeatureData: [
          {
            "title": "免费版",
            "contents": [
              {
                "content": "无限制导入与导出",
                "bg": "./assets/img/icon_feature.png"
              }
            ]
          },
          {
            "title": "学生版", // 截图中标题为「学生版」，统一表述
            "contents": [
              {
                "content": "无限制导入与导出",
                "bg": "./assets/img/icon_feature.png"
              },
              {
                "content": "无限制收件箱",
                "bg": "./assets/img/icon_feature.png"
              },
              {
                "content": "无限制多端同步 (iPad, Mac, Windows, Web)",
                "bg": "./assets/img/icon_feature.png"
              },
              {
                "content": "无限制支持API",
                "bg": "./assets/img/icon_feature.png"
              },
              {
                "content": "无限制分享链接",
                "bg": "./assets/img/icon_feature.png"
              },
              {
                "content": "无限制发布",
                "bg": "./assets/img/icon_feature.png"
              },
              {
                "content": "无限制空间成员管理",
                "bg": "./assets/img/icon_feature.png"
              }
            ]
          },
          {
            "title": "标准版",
            "contents": [
              {
                "content": "无限制导入与导出",
                "bg": "./assets/img/icon_feature.png"
              },
              {
                "content": "无限制收件箱",
                "bg": "./assets/img/icon_feature.png"
              },
              {
                "content": "无限制多端同步 (iPad, Mac, Windows, Web)",
                "bg": "./assets/img/icon_feature.png"
              },
              {
                "content": "无限制支持API",
                "bg": "./assets/img/icon_feature.png"
              },
              {
                "content": "无限制分享链接",
                "bg": "./assets/img/icon_feature.png"
              },
              {
                "content": "无限制发布",
                "bg": "./assets/img/icon_feature.png"
              },
              {
                "content": "无限制协作工作区", // 标准版特有功能
                "bg": "./assets/img/icon_feature.png"
              },
              {
                "content": "无限制空间成员管理",
                "bg": "./assets/img/icon_feature.png"
              }
            ]
          },
          {
            "title": "团队版",
            "contents": [
              {
                "content": "无限制导入与导出",
                "bg": "./assets/img/icon_feature.png"
              },
              {
                "content": "无限制收件箱",
                "bg": "./assets/img/icon_feature.png"
              },
              {
                "content": "无限制多端同步 (iPad, Mac, Windows, Web)",
                "bg": "./assets/img/icon_feature.png"
              },
              {
                "content": "无限制支持API",
                "bg": "./assets/img/icon_feature.png"
              },
              {
                "content": "无限制分享链接",
                "bg": "./assets/img/icon_feature.png"
              },
              {
                "content": "无限制发布",
                "bg": "./assets/img/icon_feature.png"
              },
              {
                "content": "无限制协作工作区", // 标准版特有功能
                "bg": "./assets/img/icon_feature.png"
              },
              {
                "content": "无限制空间成员管理",
                "bg": "./assets/img/icon_feature.png"
              }
            ]
          }
        ],
        constants: {}
      };
  },
  methods: {
    onClick_1() {
      alert(1);
    },
    changePayType(type) {
      this.payType= type;
    }
  }
};
</script>
<style scoped lang="less" src="./assets/index.response.less" />
<style scoped lang="less">
.price-cards {
  flex-wrap: wrap;

  .price-card {
    flex: 1;
  }
}

.price-feature-card {
  flex-wrap: wrap;

  .price-feature {
    flex: 1;
  }
}
</style>