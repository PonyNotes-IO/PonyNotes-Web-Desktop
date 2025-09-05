<template>
  <div class="page flex-col">
    <div class="box_2 flex-col">
      <div class="nav-bar_1 flex-row justify-between">
        <div class="group_2 flex-col">
          <div class="box_3 flex-row justify-between">
            <img
              class="thumbnail_3"
              referrerpolicy="no-referrer"
              src="./assets/img/SketchPng50df99f41c5b411f42c7412ae774fd2bb2488d446ca2d01a637b7c54af62d535.png"
            />
            <span class="text_2">小马笔记</span>
          </div>
        </div>
        <div class="group_3 flex-col">
          <div class="box_4 flex-row justify-between">
            <span class="text_3">登录</span>
            <span class="text_4">注册</span>
            <img
              class="thumbnail_4"
              referrerpolicy="no-referrer"
              src="./assets/img/SketchPng0fa58b251acd9fdd3cf775e74f4ab4baf08feb1e112b74c5ac39d12508ef9ff7.png"
            />
          </div>
        </div>
      </div>
      <div class="text-wrapper_2">
        <span class="text_5">让</span>
        <span class="text_6">笔记</span>
        <span class="text_7"></span>
        <span class="paragraph_1">
          更简单
          <br />
          让知识&nbsp;更
        </span>
        <span class="text_8">智慧</span>
      </div>
      <span class="paragraph_2">
        开源、本地优先、云同步
        <br />
        多模态记录与AI&nbsp;并存
        <br />
        助你高效构建个人与团队的知识系统
      </span>
      <div class="box_5 flex-col"></div>
    </div>
    <img
      class="image_2"
      referrerpolicy="no-referrer"
      src="./assets/img/SketchPngca0e0bb5b4aa99b2384d6fd49a06e3172a6129a30ee69f9b6f4192619a55e2b5.png"
    />
    <div class="box_6 flex-col">
      <button class="button_1 flex-col" @click="onClick_1"><span class="text_9">免费下载使用</span></button>
    </div>
    <div class="box_7 flex-col">
      <div class="tabs_1 flex-col">
        <div class="text-wrapper_3 flex-row justify-between">
          <span class="text_10">月付</span>
          <span class="text_11">年付</span>
        </div>
        <div class="block_2 flex-row justify-between">
          <div class="group_4 flex-col"></div>
          <div class="group_5 flex-col"></div>
        </div>
      </div>
      <span class="text_12">年付方案最高可节省20%</span>
      <div class="group_6 flex-row justify-between">
        <span class="text_13">免费版</span>
        <img
          class="image_3"
          referrerpolicy="no-referrer"
          src="./assets/img/SketchPngb28733a2d16c304f5e042903ea2d0e32e7148cddd4d0557688ae264f771c9cb7.png"
        />
        <span class="paragraph_3">
          本地免费使用，你的随身知识库系统，
          <br />
          AI图文生成&nbsp;+云存储，开启智能学习体验
        </span>
        <span class="text_14">0元/月/人</span>
      </div>
      <div class="group_7 flex-col">
        <div class="text-wrapper_4 flex-row">
          <span class="text_15">学生版</span>
          <span class="text_16">每月2G存储空间</span>
          <span class="text_17">3元/月/人</span>
        </div>
        <div class="box_8 flex-row">
          <img
            class="image_4"
            referrerpolicy="no-referrer"
            src="./assets/img/SketchPngb28733a2d16c304f5e042903ea2d0e32e7148cddd4d0557688ae264f771c9cb7.png"
          />
          <span class="paragraph_4">
            轻装上阵，AI助学从学生版开始小投入
            <br />
            大升级学习效率跃升一步!
            <br />
            A1图文生成&nbsp;+&nbsp;云存储开启智能学习体验
          </span>
        </div>
      </div>
      <div class="group_8 flex-col">
        <div class="text-wrapper_5 flex-row">
          <span class="text_18">标准版</span>
          <span class="text_19">每月10G存储空间</span>
          <span class="text_20">8元/月/人</span>
        </div>
        <div class="section_1 flex-row">
          <img
            class="image_5"
            referrerpolicy="no-referrer"
            src="./assets/img/SketchPngb28733a2d16c304f5e042903ea2d0e32e7148cddd4d0557688ae264f771c9cb7.png"
          />
          <span class="paragraph_5">
            向内容创作者与高效工作者的智能笔记
            <br />
            空间丰富AI+10GB云同步，
            <br />
            助你随时记录、随处创作
            <br />
            一站整合写作、整理、归档，
            <br />
            让你的笔记存的下
          </span>
        </div>
      </div>
      <div class="group_9 flex-col">
        <div class="text-wrapper_6 flex-row">
          <span class="text_21">团队版</span>
          <span class="text_22">每月10G存储空间</span>
          <span class="text_23">18元/月/人</span>
        </div>
        <div class="section_2 flex-row">
          <img
            class="image_6"
            referrerpolicy="no-referrer"
            src="./assets/img/SketchPngb28733a2d16c304f5e042903ea2d0e32e7148cddd4d0557688ae264f771c9cb7.png"
          />
          <span class="paragraph_6">
            团队协作不止是共享，更是高效共创
            <br />
            专属权限+共享工作区+大容量云空间
            <br />
            轻松协同办公，配套AI能力为团队注入
            <br />
            内容生产与决策的智能引擎
          </span>
        </div>
      </div>
      <span class="text_24">方案与功能</span>
      <div class="group_10 flex-row justify-between">
        <div class="text-group_1 flex-col">
          <span class="text_25">免费版</span>
          <span class="text_26">无限制导入与导出</span>
        </div>
        <img
          class="icon_1"
          referrerpolicy="no-referrer"
          src="./assets/img/SketchPng911352fc2ce533d840f24703c286864a9e66b89caa736d6df569061751ec921e.png"
        />
      </div>
      <div class="group_11 flex-col">
        <div class="text-wrapper_7 flex-row"><span class="text_27">学生版</span></div>
        <div class="block_3 flex-row justify-between">
          <span class="text_28">无限制导入与导出</span>
          <img
            class="thumbnail_5"
            referrerpolicy="no-referrer"
            src="./assets/img/SketchPng911352fc2ce533d840f24703c286864a9e66b89caa736d6df569061751ec921e.png"
          />
        </div>
        <div class="block_4 flex-row justify-between">
          <span class="text_29">无限制收件箱</span>
          <img
            class="thumbnail_6"
            referrerpolicy="no-referrer"
            src="./assets/img/SketchPng911352fc2ce533d840f24703c286864a9e66b89caa736d6df569061751ec921e.png"
          />
        </div>
        <div class="block_5 flex-row justify-between">
          <div class="text-wrapper_8">
            <span class="paragraph_7">
              无限制多端同步
              <br />
            </span>
            <span class="text_30">(iPad、Mac、Windows、Web)</span>
          </div>
          <img
            class="thumbnail_7"
            referrerpolicy="no-referrer"
            src="./assets/img/SketchPng911352fc2ce533d840f24703c286864a9e66b89caa736d6df569061751ec921e.png"
          />
        </div>
        <div class="block_6 flex-row justify-between">
          <span class="text_31">无限制支持API</span>
          <img
            class="thumbnail_8"
            referrerpolicy="no-referrer"
            src="./assets/img/SketchPng911352fc2ce533d840f24703c286864a9e66b89caa736d6df569061751ec921e.png"
          />
        </div>
        <div class="block_7 flex-row justify-between">
          <span class="text_32">无限制分享链接</span>
          <img
            class="thumbnail_9"
            referrerpolicy="no-referrer"
            src="./assets/img/SketchPng911352fc2ce533d840f24703c286864a9e66b89caa736d6df569061751ec921e.png"
          />
        </div>
        <div class="block_8 flex-row justify-between">
          <span class="text_33">无限制发布</span>
          <img
            class="thumbnail_10"
            referrerpolicy="no-referrer"
            src="./assets/img/SketchPng911352fc2ce533d840f24703c286864a9e66b89caa736d6df569061751ec921e.png"
          />
        </div>
        <div class="block_9 flex-row justify-between">
          <span class="text_34">无限制空间成员管理</span>
          <img
            class="thumbnail_11"
            referrerpolicy="no-referrer"
            src="./assets/img/SketchPng911352fc2ce533d840f24703c286864a9e66b89caa736d6df569061751ec921e.png"
          />
        </div>
      </div>
      <div class="list_1 flex-col">
        <div
          class="list-items_1 flex-col"
          :style="{ background: item.lanhuBg0 }"
          v-for="(item, index) in loopData0"
          :key="index"
        >
          <div class="text-wrapper_9 flex-row"><span class="text_35" v-html="item.lanhutext0"></span></div>
          <div class="group_12 flex-row justify-between">
            <span class="text_36" v-html="item.lanhutext1"></span>
            <img class="thumbnail_12" referrerpolicy="no-referrer" :src="item.lanhuimage0" />
          </div>
          <div class="group_13 flex-row justify-between">
            <span class="text_37" v-html="item.lanhutext2"></span>
            <img class="thumbnail_13" referrerpolicy="no-referrer" :src="item.lanhuimage1" />
          </div>
          <div class="group_14 flex-row justify-between">
            <div class="text-wrapper_10">
              <span class="text_38" v-html="item.lanhutext3"></span>
              <span class="text_39" v-html="item.lanhutext4"></span>
            </div>
            <img class="thumbnail_14" referrerpolicy="no-referrer" :src="item.lanhuimage2" />
          </div>
          <div class="group_15 flex-row justify-between">
            <span class="text_40" v-html="item.lanhutext5"></span>
            <img class="thumbnail_15" referrerpolicy="no-referrer" :src="item.lanhuimage3" />
          </div>
          <div class="group_16 flex-row justify-between">
            <span class="text_41" v-html="item.lanhutext6"></span>
            <img class="thumbnail_16" referrerpolicy="no-referrer" :src="item.lanhuimage4" />
          </div>
          <div class="group_17 flex-row justify-between">
            <span class="text_42" v-html="item.lanhutext7"></span>
            <img class="thumbnail_17" referrerpolicy="no-referrer" :src="item.lanhuimage5" />
          </div>
          <div class="group_18 flex-row justify-between">
            <span class="text_43" v-html="item.lanhutext8"></span>
            <img class="thumbnail_18" referrerpolicy="no-referrer" :src="item.lanhuimage6" />
          </div>
          <div class="group_19 flex-row justify-between">
            <span class="text_44" v-html="item.lanhutext9"></span>
            <img class="thumbnail_19" referrerpolicy="no-referrer" :src="item.lanhuimage7" />
          </div>
          <div v-if="item.slot1 === 1" class="group_20 flex-row justify-between">
            <span class="text_45">无限制空间成员分组</span>
            <img
              class="thumbnail_20"
              referrerpolicy="no-referrer"
              src="./assets/img/SketchPng911352fc2ce533d840f24703c286864a9e66b89caa736d6df569061751ec921e.png"
            />
          </div>
        </div>
      </div>
    </div>
    <div class="box_9 flex-col">
      <span class="text_46">常见问题</span>
      <div class="text-wrapper_11">
        <span class="text_47">1、我需要更多的云存储空间怎么办？</span>
        <span class="paragraph_8"><br /></span>
        <span class="paragraph_9">
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
        <span class="paragraph_10"><br /></span>
        <span class="text_48">空间立即生效，叠加原有容量，无需等待。</span>
      </div>
      <div class="text-wrapper_12">
        <span class="paragraph_11">
          2、AI&nbsp;对话/图片额度用完了怎么办？
          <br />
        </span>
        <span class="paragraph_12">
          你可以购买&nbsp;AI&nbsp;增值包扩容：
          <br />
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;•&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;¥10&nbsp;→&nbsp;100&nbsp;次&nbsp;+&nbsp;10&nbsp;张图像
          <br />
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;•&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;¥30&nbsp;→&nbsp;400&nbsp;次&nbsp;+&nbsp;20&nbsp;张图像
          <br />
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;•&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;¥50&nbsp;→&nbsp;1000&nbsp;次&nbsp;+&nbsp;50&nbsp;张图像
        </span>
        <span class="paragraph_13">
          <br />
          额度实时到账，不限使用期限。
        </span>
      </div>
      <div class="text-wrapper_13">
        <span class="paragraph_14">
          3、可以随时升级或降级套餐吗？
          <br />
        </span>
        <span class="paragraph_15">
          您可以随时升级套餐，但当前暂不支持主动降级。
          <br />
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;•&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;升级：支持即时生效，系统将按比例扣除剩余费
          <br />
          用，补差价即可完成升级，包含更大云存储空间、更高
          <br />
          AI额度等权益；
          <br />
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;•&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;降级：为保障数据完整性与使用连续性，当前暂
          <br />
          不支持自动降级，避免因降级导致云端数据超额或功能
          <br />
          失效。
        </span>
      </div>
      <div class="text-wrapper_14">
        <span class="paragraph_16">
          4、学生版只有学生可以买吗？
          <br />
        </span>
        <span class="paragraph_17">
          不是的，所有用户都可以购买学生版，无需学生认证。
          <br />
          我们推出学生版的初衷是为了提供一个低门槛的入门
          <br />
          体验，帮助更多用户了解和使用这款&nbsp;AI&nbsp;驱动的笔记
          <br />
          工具。学生版价格实惠，功能完整，适合：
          <br />
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;•&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;想体验我们笔记生态的用户
          <br />
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;•&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;内容不多、存储需求轻的轻度用户
          <br />
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;•&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;希望尝试&nbsp;AI&nbsp;对话、图像生成功能的新用户
        </span>
        <span class="paragraph_18">
          <br />
          无论你是学生、上班族，还是内容创作者，只要你喜欢
          <br />
          这款笔记都可以放心选择学生版开始使用！
        </span>
      </div>
      <div class="text-wrapper_15">
        <span class="paragraph_19">
          5、我的数据会因未续费而丢失吗？
          <br />
        </span>
        <span class="paragraph_20">
          不会。即使你取消了订阅，数据仍会保留在本地和云端，
          <br />
          你仍可以访问、导出、备份数据。但部分功能（如AI、
          <br />
          协作、云同步）将暂停使用，直到恢复订阅或扩容。
          <br />
          超过&nbsp;30&nbsp;天未续费的账号，其云端数据可能会被清理，
          <br />
          请及时备份或续费以避免数据丢失。
        </span>
      </div>
      <div class="text-wrapper_16">
        <span class="paragraph_21">
          6、付费订阅后是否支持退款？
          <br />
        </span>
        <span class="paragraph_22">
          目前付费订阅暂不支持退款，请在购买前充分了解产品
          <br />
          功能和订阅内容。
          <br />
          我们建议用户在订阅前通过免费版充分体验基础功能，
          <br />
          也可以选择价格更低的学生版进行试用。
          <br />
          如果你在使用过程中遇到问题，我们的客服团队会尽力
          <br />
          协助你解决。
          <br />
        </span>
        <span class="paragraph_23">
          温馨提示：AI调用额度和存储空间属于即时可用型资源
          <br />
          启用后将产生成本，因此暂不支持退款。
        </span>
      </div>
      <div class="text-wrapper_17">
        <span class="paragraph_24">
          7、可以通过第三方工具（如百度网盘、坚果云）
          <br />
          同步或备份数据吗？
          <br />
        </span>
        <span class="paragraph_25">
          我们不建议使用百度网盘、坚果云等第三方同步工具对
          <br />
          笔记数据文件夹进行直接同步。
          <br />
          这类工具并非为数据库或实时笔记文件设计，可能导致
          <br />
          如下风险：
          <br />
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;•&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;数据冲突：多端不同步可能造成笔记内容丢失或
          <br />
          版本错误；
          <br />
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;•&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;文件锁定异常：第三方同步程序可能在后台锁定
          <br />
          笔记数据库，影响正常使用；
          <br />
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;•&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;数据损坏：同步未完成或中断时，可能导致笔记
          <br />
          数据库损坏，无法恢复。
          <br />
          官方推荐的同步方式：
          <br />
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;•&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;使用软件内置的云同步机制（包括标准版和团队
          <br />
          版的云端空间），数据实时加密同步，稳定安全；
          <br />
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;•&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;如需长期备份，建议使用内置的「一键导出」功
          <br />
          能，导出为&nbsp;Markdown/PDF等开放格式，再存入网盘
          <br />
          保存。
        </span>
        <span class="paragraph_26">
          <br />
          为保障笔记完整性与稳定性，请尽量避免使用第
          <br />
          三方同步软件直接操作笔记存储路径。
        </span>
      </div>
      <div class="text-wrapper_18">
        <span class="paragraph_27">
          8、我在电脑端购买了订阅，如何在&nbsp;iPad&nbsp;等设
          <br />
          备上恢复使用？
          <br />
        </span>
        <span class="paragraph_28">
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
    <div class="box_10 flex-col">
      <img
        class="image_7"
        referrerpolicy="no-referrer"
        src="./assets/img/SketchPnge6d918b7b08b0c49f7bbe81f62c278ef96fd1a7eef15d897c2004a8a671ec00c.png"
      />
      <div class="text-wrapper_19 flex-row justify-between">
        <span class="text_49">产品</span>
        <span class="text_50">资源</span>
        <span class="text_51">联系我们</span>
      </div>
      <div class="text-wrapper_20 flex-row">
        <span class="text_52">桌面端</span>
        <span class="text_53">使用教程</span>
        <span class="text_54">B站</span>
      </div>
      <div class="text-wrapper_21 flex-row">
        <span class="text_55">手机端</span>
        <span class="text_56">快速上手</span>
        <span class="text_57">抖音</span>
      </div>
      <div class="text-wrapper_22 flex-row justify-between">
        <span class="text_58">隐私协议</span>
        <span class="text_59">常见问题</span>
        <span class="text_60">小红书</span>
      </div>
      <span class="text_61">邮箱</span>
      <img
        class="image_8"
        referrerpolicy="no-referrer"
        src="./assets/img/SketchPnge6d918b7b08b0c49f7bbe81f62c278ef96fd1a7eef15d897c2004a8a671ec00c.png"
      />
      <div class="text-wrapper_23">
        <span class="text_62">小马笔记</span>
        <span class="text_63">&nbsp;|</span>
        <span class="text_64">&nbsp;京ICP备2025131868号</span>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {
      loopData0: [
        {
          lanhuBg0:
            'url(https://lanhu-oss-proxy.lanhuapp.com/SketchPng54e295555805e861aafdbfff8a62deea21c2dfd2d30874da6028985b9f32bc70) 100% no-repeat',
          lanhutext0: '标准版',
          lanhutext1: '无限制导入与导出',
          lanhuimage0:
            './assets/img/SketchPng911352fc2ce533d840f24703c286864a9e66b89caa736d6df569061751ec921e.png',
          lanhutext2: '无限制收件箱',
          lanhuimage1:
            './assets/img/SketchPng911352fc2ce533d840f24703c286864a9e66b89caa736d6df569061751ec921e.png',
          lanhutext3: '无限制多端同步<br/>',
          lanhutext4: '(iPad、Mac、Windows、Web)',
          lanhuimage2:
            './assets/img/SketchPng911352fc2ce533d840f24703c286864a9e66b89caa736d6df569061751ec921e.png',
          lanhutext5: '无限制支持API',
          lanhuimage3:
            './assets/img/SketchPng911352fc2ce533d840f24703c286864a9e66b89caa736d6df569061751ec921e.png',
          lanhutext6: '无限制分享链接',
          lanhuimage4:
            './assets/img/SketchPng911352fc2ce533d840f24703c286864a9e66b89caa736d6df569061751ec921e.png',
          lanhutext7: '无限制发布',
          lanhuimage5:
            './assets/img/SketchPng911352fc2ce533d840f24703c286864a9e66b89caa736d6df569061751ec921e.png',
          lanhutext8: '无限制协作工作区',
          lanhuimage6:
            './assets/img/SketchPng911352fc2ce533d840f24703c286864a9e66b89caa736d6df569061751ec921e.png',
          lanhutext9: '无限制空间成员管理',
          lanhuimage7:
            './assets/img/SketchPng911352fc2ce533d840f24703c286864a9e66b89caa736d6df569061751ec921e.png'
        },
        {
          lanhuBg0:
            'url(https://lanhu-oss-proxy.lanhuapp.com/SketchPng0798822f9a7f41e56b86a6f25e80d35193d5d851c317f200088d40131dce5a1b) 100% no-repeat',
          lanhutext0: '标准版',
          lanhutext1: '无限制导入与导出',
          lanhuimage0:
            './assets/img/SketchPng911352fc2ce533d840f24703c286864a9e66b89caa736d6df569061751ec921e.png',
          lanhutext2: '无限制收件箱',
          lanhuimage1:
            './assets/img/SketchPng911352fc2ce533d840f24703c286864a9e66b89caa736d6df569061751ec921e.png',
          lanhutext3: '无限制多端同步<br/>',
          lanhutext4: '(iPad、Mac、Windows、Web)',
          lanhuimage2:
            './assets/img/SketchPng911352fc2ce533d840f24703c286864a9e66b89caa736d6df569061751ec921e.png',
          lanhutext5: '无限制支持API',
          lanhuimage3:
            './assets/img/SketchPng911352fc2ce533d840f24703c286864a9e66b89caa736d6df569061751ec921e.png',
          lanhutext6: '无限制分享链接',
          lanhuimage4:
            './assets/img/SketchPng911352fc2ce533d840f24703c286864a9e66b89caa736d6df569061751ec921e.png',
          lanhutext7: '无限制发布',
          lanhuimage5:
            './assets/img/SketchPng911352fc2ce533d840f24703c286864a9e66b89caa736d6df569061751ec921e.png',
          lanhutext8: '无限制协作工作区',
          lanhuimage6:
            './assets/img/SketchPng911352fc2ce533d840f24703c286864a9e66b89caa736d6df569061751ec921e.png',
          lanhutext9: '无限制空间成员管理',
          lanhuimage7:
            './assets/img/SketchPng911352fc2ce533d840f24703c286864a9e66b89caa736d6df569061751ec921e.png',
          slot1: 1
        }
      ],
      constants: {}
    };
  },
  methods: {
    onClick_1() {
      alert(1);
    }
  }
};
</script>
<style scoped lang="less" src="./assets/index.less" />