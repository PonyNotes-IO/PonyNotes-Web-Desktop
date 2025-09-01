<template>
  <div class="page flex-col">
    <div class="block_1 flex-col xm-center">
      <xm-nav-bar></xm-nav-bar>
      <!-- <div class="box_1 flex-row justify-between">
        <div class="text-wrapper_1 flex-col"><span class="text_1">登录</span></div>
        <div class="text-wrapper_2 flex-col"><span class="text_2">注册</span></div>
      </div> -->
      <div class="box_2 flex-row justify-between xm-center">
        <div class="section_1 flex-col justify-between">
          <div class="text-wrapper_3">
            <span class="text_3">让</span>
            <span class="text_4">笔记</span>
            <span class="paragraph_1">
              更简单
              <br />
              让知识更加
            </span>
            <span class="text_5">智慧</span>
          </div>
          <span class="paragraph_2">
            一款开源&nbsp;·&nbsp;本地优先&nbsp;·&nbsp;云同步&nbsp;·&nbsp;AI&nbsp;思考的笔记应用支持块笔记&nbsp;·&nbsp;手写&nbsp;·&nbsp;白板&nbsp;
            <br />
            ·&nbsp;PDF阅读助力个人与团队构建高效的知识系统
          </span>
          <div class="box_3 flex-row justify-between">
            <div class="block_2 flex-row">
              <div class="image-text_1 flex-row justify-between">
                <img
                  class="label_1"
                  referrerpolicy="no-referrer"
                  src="./assets/img/SketchPng630d6775ec369daac8b984dc7f5ae96401fee631aedff405d7fb0fdc9413ea2d.png"
                />
                <span class="text-group_1">开始注册</span>
              </div>
            </div>
            <div class="block_3 flex-row">
              <div class="image-text_2 flex-row justify-between">
                <img
                  class="label_2"
                  referrerpolicy="no-referrer"
                  src="./assets/img/SketchPngd2952b379366e8edf9d14d628bf98d447739c9a1f0fe337e6006a4833523babb.png"
                />
                <span class="text-group_2">立即下载</span>
              </div>
            </div>
          </div>
        </div>
        <img
          class="image_1"
          referrerpolicy="no-referrer"
          src="./assets/img/SketchPng93e045d26e009d68dfdf3b111282750281d52fc010de33ebfb15958214b0b556.png"
        />
      </div>
      <div class="box_4 flex-col"></div>
    </div>
    <div class="block_4 flex-col xm-center">
      <div class="text-wrapper_4 flex-row justify-between">
        <span class="text_6">月付</span>
        <span class="text_7">年付</span>
      </div>
      <div class="group_1 flex-row justify-between">
        <div class="group_2 flex-col"></div>
        <div class="group_3 flex-col"></div>
      </div>
      <span class="text_8">年付方案最高可节省20%</span>
      <div class="list_1 flex-row">
        <div
          class="list-items_1 flex-col"
          :style="{ background: item.lanhuBg0 }"
          v-for="(item, index) in loopData0"
          :key="index"
        >
          <span v-if="item.slot1 === 1" class="text_9">免费版</span>
          <span v-if="item.slot2 === 2" class="paragraph_3">
            本地免费使用
            <br />
            你的随身知识库系统
            <br />
            AI图文生成&nbsp;+云存储
            <br />
            开启智能学习体验
          </span>
          <div v-if="item.slot3 === 3" class="text-group_3 flex-col justify-between">
            <span class="text_10" v-html="item.specialSlot3.lanhutext0"></span>
            <span class="text_11" v-html="item.specialSlot3.lanhutext1"></span>
          </div>
          <span v-if="item.slot4 === 4" class="text_12" v-html="item.specialSlot4.lanhutext0"></span>
          <div class="text-wrapper_5 flex-col"><span class="text_13" v-html="item.lanhutext0"></span></div>
        </div>
      </div>
      <span class="text_14">方案与功能</span>
      <div class="text-wrapper_6 flex-row">
        <span class="text_15">免费版(本地)</span>
        <span class="text_16">学生版</span>
        <span class="text_17">标准版</span>
        <span class="text_18">团队版</span>
      </div>
      <div class="text-wrapper_7 flex-col"><span class="text_19">基础</span></div>
      <div class="text-wrapper_8 flex-row">
        <span class="text_20">页面/块数量</span>
        <span class="text_21">无限制</span>
        <span class="text_22">无限制</span>
        <span class="text_23">无限制</span>
        <span class="text_24">无限制</span>
      </div>
      <div class="text-wrapper_9 flex-row">
        <span class="text_25">云存储空间</span>
        <span class="text_26">——</span>
        <span class="text_27">2GB</span>
        <span class="text_28">10GB</span>
        <span class="text_29">20GB</span>
      </div>
      <div class="group_4 flex-row">
        <span class="text_30">导入与导出</span>
        <img
          class="label_3"
          referrerpolicy="no-referrer"
          src="./assets/img/SketchPng64edef59972a5a202091cbe6df0258a6281dcb67c1acb0e29d602aeec6b4f338.png"
        />
        <img
          class="label_4"
          referrerpolicy="no-referrer"
          src="./assets/img/SketchPng64edef59972a5a202091cbe6df0258a6281dcb67c1acb0e29d602aeec6b4f338.png"
        />
        <img
          class="label_5"
          referrerpolicy="no-referrer"
          src="./assets/img/SketchPng64edef59972a5a202091cbe6df0258a6281dcb67c1acb0e29d602aeec6b4f338.png"
        />
        <img
          class="label_6"
          referrerpolicy="no-referrer"
          src="./assets/img/SketchPng64edef59972a5a202091cbe6df0258a6281dcb67c1acb0e29d602aeec6b4f338.png"
        />
      </div>
      <div class="group_5 flex-row">
        <span class="text_31">收件箱</span>
        <span class="text_32">——</span>
        <img
          class="label_7"
          referrerpolicy="no-referrer"
          src="./assets/img/SketchPng64edef59972a5a202091cbe6df0258a6281dcb67c1acb0e29d602aeec6b4f338.png"
        />
        <img
          class="label_8"
          referrerpolicy="no-referrer"
          src="./assets/img/SketchPng64edef59972a5a202091cbe6df0258a6281dcb67c1acb0e29d602aeec6b4f338.png"
        />
        <img
          class="label_9"
          referrerpolicy="no-referrer"
          src="./assets/img/SketchPng64edef59972a5a202091cbe6df0258a6281dcb67c1acb0e29d602aeec6b4f338.png"
        />
      </div>
      <div class="group_6 flex-row">
        <div class="text-wrapper_10">
          <span class="text_33">多端同步</span>
          <span class="text_34">(iPad、Mac、Windows、Web)</span>
        </div>
        <span class="text_35">——</span>
        <img
          class="label_10"
          referrerpolicy="no-referrer"
          src="./assets/img/SketchPng64edef59972a5a202091cbe6df0258a6281dcb67c1acb0e29d602aeec6b4f338.png"
        />
        <img
          class="label_11"
          referrerpolicy="no-referrer"
          src="./assets/img/SketchPng64edef59972a5a202091cbe6df0258a6281dcb67c1acb0e29d602aeec6b4f338.png"
        />
        <img
          class="label_12"
          referrerpolicy="no-referrer"
          src="./assets/img/SketchPng64edef59972a5a202091cbe6df0258a6281dcb67c1acb0e29d602aeec6b4f338.png"
        />
      </div>
      <div class="group_7 flex-row">
        <span class="text_36">支持&nbsp;API</span>
        <span class="text_37">——</span>
        <img
          class="label_13"
          referrerpolicy="no-referrer"
          src="./assets/img/SketchPng64edef59972a5a202091cbe6df0258a6281dcb67c1acb0e29d602aeec6b4f338.png"
        />
        <img
          class="label_14"
          referrerpolicy="no-referrer"
          src="./assets/img/SketchPng64edef59972a5a202091cbe6df0258a6281dcb67c1acb0e29d602aeec6b4f338.png"
        />
        <img
          class="label_15"
          referrerpolicy="no-referrer"
          src="./assets/img/SketchPng64edef59972a5a202091cbe6df0258a6281dcb67c1acb0e29d602aeec6b4f338.png"
        />
      </div>
      <div class="text-wrapper_11 flex-row">
        <span class="text_38">版本历史</span>
        <span class="text_39">——</span>
        <span class="text_40">7天</span>
        <span class="text_41">7天</span>
        <span class="text_42">30天</span>
      </div>
      <div class="text-wrapper_12 flex-col"><span class="text_43">AI功能</span></div>
      <div class="text-wrapper_13 flex-row">
        <span class="text_44">AI对话</span>
        <span class="text_45">——</span>
        <span class="text_46">10次/月</span>
        <span class="text_47">40次/月</span>
        <span class="text_48">120次/月</span>
      </div>
      <div class="text-wrapper_14 flex-row">
        <span class="text_49">图片生成</span>
        <span class="text_50">——</span>
        <span class="text_51">10张/月</span>
        <span class="text_52">20张/月</span>
      </div>
      <div class="text-wrapper_15 flex-col"><span class="text_53">共享和协作</span></div>
      <div class="group_8 flex-row">
        <span class="text_54">分享链接</span>
        <span class="text_55">——</span>
        <img
          class="label_16"
          referrerpolicy="no-referrer"
          src="./assets/img/SketchPng64edef59972a5a202091cbe6df0258a6281dcb67c1acb0e29d602aeec6b4f338.png"
        />
        <img
          class="label_17"
          referrerpolicy="no-referrer"
          src="./assets/img/SketchPng64edef59972a5a202091cbe6df0258a6281dcb67c1acb0e29d602aeec6b4f338.png"
        />
        <img
          class="label_18"
          referrerpolicy="no-referrer"
          src="./assets/img/SketchPng64edef59972a5a202091cbe6df0258a6281dcb67c1acb0e29d602aeec6b4f338.png"
        />
      </div>
      <div class="group_9 flex-row">
        <span class="text_56">发布</span>
        <span class="text_57">——</span>
        <img
          class="label_19"
          referrerpolicy="no-referrer"
          src="./assets/img/SketchPng64edef59972a5a202091cbe6df0258a6281dcb67c1acb0e29d602aeec6b4f338.png"
        />
        <img
          class="label_20"
          referrerpolicy="no-referrer"
          src="./assets/img/SketchPng64edef59972a5a202091cbe6df0258a6281dcb67c1acb0e29d602aeec6b4f338.png"
        />
        <img
          class="label_21"
          referrerpolicy="no-referrer"
          src="./assets/img/SketchPng64edef59972a5a202091cbe6df0258a6281dcb67c1acb0e29d602aeec6b4f338.png"
        />
      </div>
      <div class="text-wrapper_16 flex-row">
        <span class="text_58">工作区成员</span>
        <span class="text_59">——</span>
        <span class="text_60">2</span>
        <span class="text_61">5</span>
        <span class="text_62">10</span>
      </div>
      <div class="group_10 flex-row">
        <span class="text_63">协作工作区</span>
        <span class="text_64">——</span>
        <span class="text_65">仅限1</span>
        <img
          class="label_22"
          referrerpolicy="no-referrer"
          src="./assets/img/SketchPng64edef59972a5a202091cbe6df0258a6281dcb67c1acb0e29d602aeec6b4f338.png"
        />
        <img
          class="label_23"
          referrerpolicy="no-referrer"
          src="./assets/img/SketchPng64edef59972a5a202091cbe6df0258a6281dcb67c1acb0e29d602aeec6b4f338.png"
        />
      </div>
      <div class="text-wrapper_17 flex-col"><span class="text_66">安全与权限管理</span></div>
      <div class="text-wrapper_18 flex-row">
        <span class="text_67">页面权限管理</span>
        <span class="text_68">仅查看</span>
        <span class="text_69">10个访客编辑</span>
        <span class="text_70">50个访客编辑</span>
      </div>
      <div class="group_11 flex-row">
        <span class="text_71">空间成员管理</span>
        <span class="text_72">——</span>
        <img
          class="label_24"
          referrerpolicy="no-referrer"
          src="./assets/img/SketchPng64edef59972a5a202091cbe6df0258a6281dcb67c1acb0e29d602aeec6b4f338.png"
        />
        <img
          class="label_25"
          referrerpolicy="no-referrer"
          src="./assets/img/SketchPng64edef59972a5a202091cbe6df0258a6281dcb67c1acb0e29d602aeec6b4f338.png"
        />
        <img
          class="label_26"
          referrerpolicy="no-referrer"
          src="./assets/img/SketchPng64edef59972a5a202091cbe6df0258a6281dcb67c1acb0e29d602aeec6b4f338.png"
        />
      </div>
      <div class="group_12 flex-row">
        <span class="text_73">空间成员分组</span>
        <span class="text_74">——</span>
        <img
          class="label_27"
          referrerpolicy="no-referrer"
          src="./assets/img/SketchPng64edef59972a5a202091cbe6df0258a6281dcb67c1acb0e29d602aeec6b4f338.png"
        />
      </div>
    </div>
    <div class="block_5 flex-col xm-center">
      <span class="text_75">常见问题</span>
      <div class="text-wrapper_19">
        <span class="text_76">1、我需要更多的云存储空间怎么办？</span>
        <span class="paragraph_4"><br /></span>
        <span class="paragraph_5">
          你可以随时通过「账户设置&nbsp;→&nbsp;存储扩容」购买额外空间。我们提供：
          <br />
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;•&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;5GB&nbsp;¥5/月
          <br />
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;•&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;20GB&nbsp;¥15/月
          <br />
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;•&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;50GB&nbsp;¥35/月
        </span>
        <span class="paragraph_6"><br /></span>
        <span class="text_77">空间立即生效，叠加原有容量，无需等待。</span>
      </div>
      <div class="text-wrapper_20">
        <span class="paragraph_7">
          2、AI&nbsp;对话/图片额度用完了怎么办？
          <br />
        </span>
        <span class="paragraph_8">
          你可以购买&nbsp;AI&nbsp;增值包扩容：
          <br />
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;•&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;¥10&nbsp;→&nbsp;100&nbsp;次&nbsp;+&nbsp;10&nbsp;张图像
          <br />
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;•&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;¥30&nbsp;→&nbsp;400&nbsp;次&nbsp;+&nbsp;20&nbsp;张图像
          <br />
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;•&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;¥50&nbsp;→&nbsp;1000&nbsp;次&nbsp;+&nbsp;50&nbsp;张图像
          <br />
        </span>
        <span class="text_78">额度实时到账，不限使用期限。</span>
      </div>
      <div class="text-wrapper_21">
        <span class="paragraph_9">
          3、可以随时升级或降级套餐吗？
          <br />
        </span>
        <span class="paragraph_10">
          您可以随时升级套餐，但当前暂不支持主动降级。
          <br />
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;•&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;升级：支持即时生效，系统将按比例扣除剩余费用，补差价即可完成升级，包含更大云存
          <br />
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;储空间、更高AI额度等权益；
          <br />
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;•&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;降级：为保障数据完整性与使用连续性，当前暂不支持自动降级，避免因降级导致云端数
          <br />
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;据超额或功能失效。
        </span>
      </div>
      <div class="text-wrapper_22">
        <span class="paragraph_11">
          4、学生版只有学生可以买吗？
          <br />
        </span>
        <span class="paragraph_12">
          不是的，所有用户都可以购买学生版，无需学生认证。我们推出学生版的初衷是为了提供一个低
          <br />
          门槛的入门体验，帮助更多用户了解和使用这款&nbsp;AI&nbsp;驱动的笔记工具。
          <br />
          学生版价格实惠，功能完整，适合：
          <br />
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;•&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;想体验我们笔记生态的用户
          <br />
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;•&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;内容不多、存储需求轻的轻度用户
          <br />
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;•&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;希望尝试&nbsp;AI&nbsp;对话、图像生成功能的新用户
          <br />
        </span>
        <span class="text_79">
          无论你是学生、上班族，还是内容创作者，只要你喜欢这款笔记都可以放心选择学生版开始使用！
        </span>
      </div>
      <div class="text-wrapper_23">
        <span class="paragraph_13">
          5、我的数据会因未续费而丢失吗？
          <br />
        </span>
        <span class="paragraph_14">
          不会。即使你取消了订阅，数据仍会保留在本地和云端，你仍可以访问、导出、备份数据。但部
          <br />
          分功能（如AI、协作、云同步）将暂停使用，直到恢复订阅或扩容。
          <br />
          超过&nbsp;30&nbsp;天未续费的账号，其云端数据可能会被清理，请及时备份或续费以避免数据丢失。
        </span>
      </div>
      <div class="text-wrapper_24">
        <span class="text_80">6、付费订阅后是否支持退款？</span>
        <span class="paragraph_15"><br /></span>
        <span class="paragraph_16">
          目前付费订阅暂不支持退款，请在购买前充分了解产品功能和订阅内容。
          <br />
          我们建议用户在订阅前通过免费版充分体验基础功能，也可以选择价格更低的学生版进行试用。
          <br />
          如果你在使用过程中遇到问题，我们的客服团队会尽力协助你解决。
          <br />
        </span>
        <span class="text_81">
          温馨提示：AI调用额度和存储空间属于即时可用型资源，启用后将产生成本，因此暂不支持退款。
        </span>
      </div>
      <div class="text-wrapper_25">
        <span class="paragraph_17">
          7、可以通过第三方工具（如百度网盘、坚果云）同步或备份数据吗？
          <br />
        </span>
        <span class="paragraph_18">
          我们不建议使用百度网盘、坚果云等第三方同步工具对笔记数据文件夹进行直接同步。
          <br />
          这类工具并非为数据库或实时笔记文件设计，可能导致如下风险：
          <br />
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;•&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;数据冲突：多端不同步可能造成笔记内容丢失或版本错误；
          <br />
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;•&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;文件锁定异常：第三方同步程序可能在后台锁定笔记数据库，影响正常使用；
          <br />
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;•&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;数据损坏：同步未完成或中断时，可能导致笔记数据库损坏，无法恢复。
          <br />
          官方推荐的同步方式：
          <br />
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;•&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;使用软件内置的云同步机制（包括标准版和团队版的云端空间），数据实时加密同步，稳
          <br />
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;定安全；
          <br />
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;•&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;如需长期备份，建议使用内置的「一键导出」功能，导出为&nbsp;Markdown/PDF等开放格式，
          <br />
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;再存入网盘保存。
          <br />
        </span>
        <span class="text_82">为保障笔记完整性与稳定性，请尽量避免使用第三方同步软件直接操作笔记存储路径。</span>
      </div>
      <div class="text-wrapper_26">
        <span class="text_83">8、我在电脑端购买了订阅，如何在&nbsp;iPad&nbsp;等设备上恢复使用？</span>
        <span class="paragraph_19"><br /></span>
        <span class="paragraph_20">
          只需使用相同账号登录，系统将自动同步您的订阅权益。
          <br />
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;•&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;无论您通过&nbsp;官网、桌面端&nbsp;还是&nbsp;App&nbsp;端&nbsp;完成购买，只要登录的是同一账户，订阅状态将在
          <br />
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;各端同步；
          <br />
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;•&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;如果未自动恢复，请前往&nbsp;「账户设置&nbsp;→&nbsp;恢复购买」&nbsp;尝试手动同步；
          <br />
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;•&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;若仍未恢复，请确认&nbsp;iPad&nbsp;登录账号与购买账号一致，或联系客户支持协助处理。
          <br />
        </span>
        <span class="paragraph_21">
          温馨提示：Apple&nbsp;内购购买的订阅需通过&nbsp;App&nbsp;Store&nbsp;登录同一&nbsp;Apple&nbsp;ID&nbsp;并点击「恢复购买」才
          <br />
          能生效。
        </span>
      </div>
      <div class="box_5 flex-col"></div>
    </div>
    <div class="block_6 flex-col xm-center">
      <div class="text-wrapper_27 flex-row">
        <span class="text_84">产品</span>
        <span class="text_85">资源</span>
        <span class="text_86">联系我们</span>
      </div>
      <div class="group_13 flex-row">
        <div class="image-text_3 flex-row justify-between">
          <img
            class="image_2"
            referrerpolicy="no-referrer"
            src="./assets/img/SketchPng8491b7fa6bd5c8da65d17c8668d288a321bcf44861191fb8b20d13a832128b2c.png"
          />
          <span class="text-group_4">小马笔记</span>
        </div>
        <div class="text-wrapper_28 flex-col justify-between">
          <span class="text_87">桌面端</span>
          <span class="text_88">手机端</span>
        </div>
        <div class="text-wrapper_29 flex-col justify-between">
          <span class="text_89">使用教程</span>
          <span class="text_90">快速上手</span>
        </div>
        <div class="text-wrapper_30 flex-col justify-between">
          <span class="text_91">B站</span>
          <span class="text_92">抖音</span>
        </div>
      </div>
      <div class="text-wrapper_31 flex-row">
        <span class="text_93">京ICP备2025131868号</span>
        <span class="text_94">隐私协议</span>
        <span class="text_95">常见问题</span>
        <span class="text_96">小红书</span>
      </div>
      <div class="text-wrapper_32 flex-row justify-between">
        <span class="text_97">用户协议</span>
        <span class="text_98">邮箱</span>
      </div>
    </div>
    
  </div>
</template>
<script>
import XmNavBar from '@/components/xm-nav-bar.vue';
export default {
  components:{
    XmNavBar
  },
  data() {
    return {
      loopData0: [
        {
          lanhuBg0:
            'url(https://lanhu-dds-backend.oss-cn-beijing.aliyuncs.com/merge_image/imgs/a5e7c08deac648feb33e422c8243aca7_mergeImage.png)',
          lanhutext0: '开始使用',
          slot1: 1,
          slot2: 2
        },
        {
          lanhuBg0:
            'url(https://lanhu-dds-backend.oss-cn-beijing.aliyuncs.com/merge_image/imgs/96b664017dbc411db5d4bfa5a95298c4_mergeImage.png)',
          lanhutext0: '开始使用',
          specialSlot3: { lanhutext0: '学生版', lanhutext1: '每月2G存储空间' },
          slot3: 3,
          specialSlot4: {
            lanhutext0:
              '轻装上阵，AI助学<br/>从学生版开始小投入<br/>大升级学习效率跃升一步!<br/>A1图文生成&nbsp;+&nbsp;云存储<br/>开启智能学习体验'
          },
          slot4: 4
        },
        {
          lanhuBg0:
            'url(https://lanhu-dds-backend.oss-cn-beijing.aliyuncs.com/merge_image/imgs/4cd252c347224a60ac63de3d0dc347af_mergeImage.png)',
          lanhutext0: '开始使用',
          specialSlot3: { lanhutext0: '标准版', lanhutext1: '每月10G存储空间' },
          slot3: 3,
          specialSlot4: {
            lanhutext0:
              '向内容创作者<br/>与高效工作者的智能笔记<br/>空间丰富AI+10GB云同步<br/>助你随时记录、随处创作<br/>一站整合写作、整理、归档，<br/>让你的笔记存的下'
          },
          slot4: 4
        },
        {
          lanhuBg0:
            'url(https://lanhu-dds-backend.oss-cn-beijing.aliyuncs.com/merge_image/imgs/a6388b70759149dbaa8ee6abc2d4dab6_mergeImage.png)',
          lanhutext0: '开始使用',
          specialSlot3: { lanhutext0: '团队版', lanhutext1: '每月10G存储空间' },
          slot3: 3,
          specialSlot4: {
            lanhutext0:
              '团队协作不止是共享<br/>更是高效共创<br/>专属权限+共享工作区+大容量云空间<br/>轻松协同办公<br/>配套AI能力为团队注入<br/>内容生产与决策的智能引擎'
          },
          slot4: 4
        }
      ],
      constants: {}
    };
  },
  methods: {}
};
</script>
<style scoped lang="less" src="./assets/index.less" />