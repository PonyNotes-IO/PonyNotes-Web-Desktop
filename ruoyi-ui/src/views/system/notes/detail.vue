<template>
  <div class="app-container">
    <el-form :model="form" label-width="100px">
      <el-form-item label="笔记ID">
        <el-input v-model="form.id" readonly disabled />
      </el-form-item>
      <el-form-item label="笔记标题">
        <el-input v-model="form.title" readonly disabled />
      </el-form-item>
      <el-form-item label="笔记内容">
        <el-input
          v-model="form.content"
          type="textarea"
          readonly
          disabled
          :rows="10"
        />
      </el-form-item>
      <el-form-item label="创建者ID">
        <el-input v-model="form.userId" readonly disabled />
      </el-form-item>
      <el-form-item label="创建者名称">
        <el-input v-model="form.userName" readonly disabled />
      </el-form-item>
      <el-form-item label="分类ID">
        <el-input v-model="form.categoryId" readonly disabled />
      </el-form-item>
      <el-form-item label="标签">
        <el-input v-model="form.tags" readonly disabled />
      </el-form-item>
      <el-form-item label="是否公开">
        <el-tag :type="form.isPublic === 1 ? 'success' : 'info'" readonly>{{ form.isPublic === 1 ? '公开' : '私有' }}</el-tag>
      </el-form-item>
      <el-form-item label="状态">
        <el-tag :type="form.status === '0' ? 'success' : 'danger'" readonly>{{ form.status === '0' ? '正常' : '停用' }}</el-tag>
      </el-form-item>
      <el-form-item label="创建时间">
        <el-input v-model="form.createTime" readonly disabled />
      </el-form-item>
      <el-form-item label="更新时间">
        <el-input v-model="form.updateTime" readonly disabled />
      </el-form-item>
      <el-form-item label="备注">
        <el-input v-model="form.remark" type="textarea" readonly disabled />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="goBack">返回</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { getNotes } from '@/api/system/notes'

export default {
  name: 'NotesDetail',
  data() {
    return {
      // 表单参数
      form: {
        id: undefined,
        title: undefined,
        content: undefined,
        userId: undefined,
        userName: undefined,
        categoryId: undefined,
        tags: undefined,
        isPublic: 0,
        status: '0',
        createTime: undefined,
        updateTime: undefined,
        remark: undefined
      }
    }
  },
  created() {
    const id = this.$route.params.id
    this.getNotesDetail(id)
  },
  methods: {
    /** 获取笔记详情 */
    getNotesDetail(id) {
      getNotes(id).then(response => {
        this.form = response.data
      })
    },
    /** 返回上一页 */
    goBack() {
      this.$router.push('/system/notes')
    }
  }
}
</script>
