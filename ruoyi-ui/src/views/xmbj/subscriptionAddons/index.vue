<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="补充包代码" prop="addonCode">
        <el-input
          v-model="queryParams.addonCode"
          placeholder="请输入补充包代码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="补充包名称" prop="addonName">
        <el-input
          v-model="queryParams.addonName"
          placeholder="请输入补充包名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="补充包中文名称" prop="addonNameCn">
        <el-input
          v-model="queryParams.addonNameCn"
          placeholder="请输入补充包中文名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="补充包类型" prop="addonType">
        <el-select v-model="queryParams.addonType" placeholder="请选择补充包类型" clearable>
          <el-option label="存储" value="storage" />
          <el-option label="AI令牌" value="ai_token" />
        </el-select>
      </el-form-item>
      <el-form-item label="是否激活" prop="isActive">
        <el-select v-model="queryParams.isActive" placeholder="请选择是否激活" clearable>
          <el-option label="激活" :value="true" />
          <el-option label="未激活" :value="false" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['xmbj:subscriptionAddons:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['xmbj:subscriptionAddons:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['xmbj:subscriptionAddons:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['xmbj:subscriptionAddons:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="subscriptionAddonsList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="补充包ID" align="center" prop="id" width="80" />
      <el-table-column label="补充包代码" align="center" prop="addonCode" width="120" />
      <el-table-column label="补充包名称" align="center" prop="addonName" width="150" />
      <el-table-column label="补充包中文名称" align="center" prop="addonNameCn" width="150" />
      <el-table-column label="补充包类型" align="center" prop="addonType" width="100">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.addonType === 'storage'" type="success">存储</el-tag>
          <el-tag v-else-if="scope.row.addonType === 'ai_token'" type="primary">AI令牌</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="价格（元）" align="center" prop="priceYuan" width="100" />
      <el-table-column label="存储空间（GB）" align="center" prop="storageGb" width="120">
        <template slot-scope="scope">
          <span v-if="scope.row.addonType === 'storage'">{{ scope.row.storageGb }}</span>
          <span v-else>-</span>
        </template>
      </el-table-column>
      <el-table-column label="AI对话次数" align="center" prop="aiChatCount" width="120">
        <template slot-scope="scope">
          <span v-if="scope.row.addonType === 'ai_token'">{{ scope.row.aiChatCount }}</span>
          <span v-else>-</span>
        </template>
      </el-table-column>
      <el-table-column label="AI图片生成次数" align="center" prop="aiImageCount" width="140">
        <template slot-scope="scope">
          <span v-if="scope.row.addonType === 'ai_token'">{{ scope.row.aiImageCount }}</span>
          <span v-else>-</span>
        </template>
      </el-table-column>
      <el-table-column label="是否激活" align="center" prop="isActive" width="100">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.isActive === true" type="success">激活</el-tag>
          <el-tag v-else-if="scope.row.isActive === false" type="info">未激活</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createdAt" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createdAt, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="更新时间" align="center" prop="updatedAt" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updatedAt, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="180">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['xmbj:subscriptionAddons:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['xmbj:subscriptionAddons:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="140px">
        <el-form-item label="补充包代码" prop="addonCode">
          <el-input v-model="form.addonCode" placeholder="请输入补充包代码" />
        </el-form-item>
        <el-form-item label="补充包名称" prop="addonName">
          <el-input v-model="form.addonName" placeholder="请输入补充包名称" />
        </el-form-item>
        <el-form-item label="补充包中文名称" prop="addonNameCn">
          <el-input v-model="form.addonNameCn" placeholder="请输入补充包中文名称" />
        </el-form-item>
        <el-form-item label="补充包类型" prop="addonType">
          <el-select v-model="form.addonType" placeholder="请选择补充包类型" @change="handleAddonTypeChange">
            <el-option label="存储" value="storage" />
            <el-option label="AI令牌" value="ai_token" />
          </el-select>
        </el-form-item>
        <el-form-item label="价格（元）" prop="priceYuan">
          <el-input-number v-model="form.priceYuan" :precision="2" :min="0" placeholder="请输入价格" />
        </el-form-item>
        <el-form-item label="存储空间（GB）" prop="storageGb" v-if="form.addonType === 'storage'">
          <el-input-number v-model="form.storageGb" :min="0" placeholder="请输入存储空间" />
        </el-form-item>
        <el-form-item label="AI对话次数" prop="aiChatCount" v-if="form.addonType === 'ai_token'">
          <el-input-number v-model="form.aiChatCount" :min="0" placeholder="请输入AI对话次数" />
        </el-form-item>
        <el-form-item label="AI图片生成次数" prop="aiImageCount" v-if="form.addonType === 'ai_token'">
          <el-input-number v-model="form.aiImageCount" :min="0" placeholder="请输入AI图片生成次数" />
        </el-form-item>
        <el-form-item label="是否激活" prop="isActive">
          <el-radio-group v-model="form.isActive">
            <el-radio :label="true">激活</el-radio>
            <el-radio :label="false">未激活</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listSubscriptionAddons, getSubscriptionAddons, delSubscriptionAddons, addSubscriptionAddons, updateSubscriptionAddons, exportSubscriptionAddons } from "@/api/xmbj/subscriptionAddons"

export default {
  name: "SubscriptionAddons",
  data() {
    return {
      loading: true,
      ids: [],
      single: true,
      multiple: true,
      showSearch: true,
      total: 0,
      subscriptionAddonsList: [],
      title: "",
      open: false,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        addonCode: null,
        addonName: null,
        addonNameCn: null,
        addonType: null,
        isActive: null
      },
      form: {},
      rules: {
        addonCode: [
          { required: true, message: "补充包代码不能为空", trigger: "blur" }
        ],
        addonName: [
          { required: true, message: "补充包名称不能为空", trigger: "blur" }
        ],
        addonNameCn: [
          { required: true, message: "补充包中文名称不能为空", trigger: "blur" }
        ],
        addonType: [
          { required: true, message: "补充包类型不能为空", trigger: "change" }
        ],
        priceYuan: [
          { required: true, message: "价格不能为空", trigger: "blur" }
        ],
        isActive: [
          { required: true, message: "是否激活不能为空", trigger: "change" }
        ]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.loading = true
      listSubscriptionAddons(this.queryParams).then(response => {
        this.subscriptionAddonsList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    cancel() {
      this.open = false
      this.reset()
    },
    reset() {
      this.form = {
        id: null,
        addonCode: null,
        addonName: null,
        addonNameCn: null,
        addonType: "storage",
        priceYuan: null,
        storageGb: null,
        aiChatCount: null,
        aiImageCount: null,
        isActive: true,
        createdAt: null,
        updatedAt: null
      }
      this.resetForm("form")
    },
    handleAddonTypeChange(value) {
      if (value === 'storage') {
        this.form.storageGb = null
        this.form.aiChatCount = null
        this.form.aiImageCount = null
      } else if (value === 'ai_token') {
        this.form.storageGb = null
        this.form.aiChatCount = null
        this.form.aiImageCount = null
      }
    },
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    resetQuery() {
      this.resetForm("queryForm")
      this.handleQuery()
    },
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加订阅补充包"
    },
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids[0]
      getSubscriptionAddons(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改订阅补充包"
      })
    },
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateSubscriptionAddons(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addSubscriptionAddons(this.form).then(response => {
              this.$modal.msgSuccess("新增成功")
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$modal.confirm('是否确认删除订阅补充包编号为"' + ids + '"的数据项？').then(function() {
        return delSubscriptionAddons(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    handleExport() {
      this.download('xmbj/subscriptionAddons/export', {
        ...this.queryParams
      }, `subscriptionaddons_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
