// toast.ts - 不依赖DOM的Toast弹窗工具

interface ToastOptions {
  message: string;
  type?: 'success' | 'error' | 'warning' | 'info';
  duration?: number;
  position?: 'top-right' | 'top-center' | 'top-left' | 'bottom-right' | 'bottom-center' | 'bottom-left';
}

class Toast {
  private static containerMap: Record<string, HTMLElement> = {};
  private static toastQueue: HTMLElement[] = [];

  // 获取或创建toast容器
  private static getContainer(position: string): HTMLElement {
    if (!this.containerMap[position]) {
      const container = document.createElement('div');
      container.className = `fixed z-1650 flex flex-col gap-2 p-4`;
      
      // 根据位置设置样式
      switch (position) {
        case 'top-right':
          container.style.top = '4rem';
          container.style.right = '1rem';
          break;
        case 'top-center':
          container.style.top = '4rem';
          container.style.left = '50%';
          container.style.transform = 'translateX(-50%)';
          break;
        case 'top-left':
          container.style.top = '4rem';
          container.style.left = '1rem';
          break;
        case 'bottom-right':
          container.style.bottom = '1rem';
          container.style.right = '1rem';
          break;
        case 'bottom-center':
          container.style.bottom = '1rem';
          container.style.left = '50%';
          container.style.transform = 'translateX(-50%)';
          break;
        case 'bottom-left':
          container.style.bottom = '1rem';
          container.style.left = '1rem';
          break;
      }

      document.body.appendChild(container);
      this.containerMap[position] = container;
    }
    return this.containerMap[position];
  }

  // 创建toast元素
  private static createToast(options: ToastOptions): HTMLElement {
    const {
      message,
      type = 'success',
      position = 'top-right'
    } = options;

    const toast = document.createElement('div');
    
    // 设置toast样式
    toast.className = `max-w-xs p-4 rounded-lg shadow-lg flex items-start gap-2 transition-all duration-300 ease-in-out transform opacity-0 translate-x-full`;
    
    // 根据类型设置背景色
    switch (type) {
      case 'success':
        toast.classList.add('bg-green-500', 'text-white');
        break;
      case 'error':
        toast.classList.add('bg-red-500', 'text-white');
        break;
      case 'warning':
        toast.classList.add('bg-yellow-500', 'text-white');
        break;
      case 'info':
        toast.classList.add('bg-blue-500', 'text-white');
        break;
    }

    // 创建toast内容
    toast.innerHTML = `
      <div class="flex-1">
        ${message}
      </div>
    `;

    return toast;
  }

  // 显示toast
  private static showToast(toast: HTMLElement, container: HTMLElement, duration: number) {
    // 添加到容器
    container.appendChild(toast);
    
    // 触发动画
    setTimeout(() => {
      toast.classList.remove('opacity-0', 'translate-x-full');
    }, 10);

    // 添加到队列
    this.toastQueue.push(toast);

    // 设置自动关闭
    if (duration > 0) {
      setTimeout(() => {
        this.hideToast(toast);
      }, duration);
    }
  }

  // 隐藏toast
  private static hideToast(toast: HTMLElement) {
    // 触发退出动画
    toast.classList.add('opacity-0', 'translate-x-full');

    // 动画结束后移除元素
    setTimeout(() => {
      if (toast.parentNode) {
        toast.parentNode.removeChild(toast);
      }
      // 从队列中移除
      const index = this.toastQueue.indexOf(toast);
      if (index > -1) {
        this.toastQueue.splice(index, 1);
      }
    }, 300);
  }

  // 成功提示
  static success(message: string, options?: Partial<ToastOptions>) {
    this.show({
      message,
      type: 'success',
      duration: 3000,
      ...options
    });
  }

  // 错误提示
  static error(message: string, options?: Partial<ToastOptions>) {
    this.show({
      message,
      type: 'error',
      duration: 4000,
      ...options
    });
  }

  // 警告提示
  static warning(message: string, options?: Partial<ToastOptions>) {
    this.show({
      message,
      type: 'warning',
      duration: 3500,
      ...options
    });
  }

  // 信息提示
  static info(message: string, options?: Partial<ToastOptions>) {
    this.show({
      message,
      type: 'info',
      duration: 3000,
      ...options
    });
  }

  // 显示toast
  static show(options: ToastOptions) {
    const {
      // position = 'top-right',
      position = 'top-center',
      duration = 3000
    } = options;

    const container = this.getContainer(position);
    const toast = this.createToast(options);
    this.showToast(toast, container, duration);
  }

  // 清除所有toast
  static clear() {
    this.toastQueue.forEach(toast => {
      this.hideToast(toast);
    });
    this.toastQueue = [];
  }
}

export default Toast;