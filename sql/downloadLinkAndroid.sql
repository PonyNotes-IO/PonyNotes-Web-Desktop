-- 官网桌面端「Android/Pad」下载入口。保留后台已有配置（包括停用记录）。
INSERT INTO download_link (platform, arch_label, version, download_url, sort_order, status)
SELECT 'Android/Pad', '通用版', '1.2.13',
       'https://oss.xiaomabiji.com/releases/1.2.13/PonyNotes-1.2.13-android-universal.apk',
       4, 1
WHERE NOT EXISTS (
    SELECT 1 FROM download_link WHERE platform = 'Android/Pad' AND del_flag = 0
);
