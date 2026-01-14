export const useDeviceDetection = () => {
    const isMobile = ref(false)
    const isTablet = ref(false)
    const isDesktop = ref(false)

    const checkDevice = () => {
        if (process.client) {
            const width = window.innerWidth
            isMobile.value = width < 768
            isTablet.value = width >= 768 && width < 1024
            isDesktop.value = width >= 1024
        }
    }

    onMounted(() => {
        checkDevice()
        window.addEventListener('resize', checkDevice)
    })

    onUnmounted(() => {
        window.removeEventListener('resize', checkDevice)
    })

    return {
        isMobile,
        isTablet,
        isDesktop
    }
}
