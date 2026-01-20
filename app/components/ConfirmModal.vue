<script setup>
const props = defineProps({
    isVisible: {
        type: Boolean,
        default: false
    },
    message: {
        type: String,
        default: '确定要执行此操作吗？'
    }
})

const emit = defineEmits(['confirm', 'cancel'])

const handleConfirm = () => {
    emit('confirm')
}

const handleCancel = () => {
    emit('cancel')
}
</script>

<template>
    <Teleport to="body">
        <div v-if="props.isVisible" 
            class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-[10000]"
            @click="handleCancel">
            
            <div @click.stop 
                class="bg-white rounded-2xl px-8 py-6 max-w-[400px] w-[90%] shadow-2xl">
                
                <!-- 提示消息 -->
                <p class="text-[16px] text-gray-900 font-medium mb-6 text-center">
                    {{ props.message }}
                </p>
                
                <!-- 按钮组 -->
                <div class="flex gap-3 justify-center">
                    <button @click="handleCancel"
                        class="flex-1 px-6 py-2.5 rounded-lg bg-gray-100 text-gray-700 font-bold transition-all hover:bg-gray-200 border-none shadow-none">
                        取消
                    </button>
                    <button @click="handleConfirm"
                        class="flex-1 px-6 py-2.5 rounded-lg bg-[#FF4D00] text-white font-bold transition-all hover:bg-opacity-90 border-none shadow-none">
                        确定
                    </button>
                </div>
            </div>
        </div>
    </Teleport>
</template>
