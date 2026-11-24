export function mobileDownloadPlatform(device: Pick<Navigator, 'userAgent' | 'maxTouchPoints'>) {
  if (/Android/i.test(device.userAgent)) return 'Android/Pad';
  if (/iPad|iPhone|iPod/i.test(device.userAgent) ||
      (/Macintosh/i.test(device.userAgent) && device.maxTouchPoints > 1)) {
    return 'iPhone/iPad';
  }
  return null;
}
