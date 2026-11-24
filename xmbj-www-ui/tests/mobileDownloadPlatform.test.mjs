import assert from 'node:assert/strict';
import { test } from 'node:test';
import { mobileDownloadPlatform } from '../app/utils/mobileDownloadPlatform.ts';

test('routes phones and desktop-mode iPads to their store while desktops keep file downloads', () => {
  for (const [userAgent, maxTouchPoints, expected] of [
    ['Linux; Android 15; Pixel 9', 5, 'Android/Pad'],
    ['iPhone; CPU iPhone OS 18_0 like Mac OS X', 5, 'iPhone/iPad'],
    ['iPad; CPU OS 18_0 like Mac OS X', 5, 'iPhone/iPad'],
    ['Macintosh; Intel Mac OS X 10_15', 5, 'iPhone/iPad'],
    ['Macintosh; Intel Mac OS X 10_15', 0, null],
    ['Windows NT 10.0; Win64; x64', 10, null],
  ]) {
    assert.equal(mobileDownloadPlatform({ userAgent, maxTouchPoints }), expected, userAgent);
  }
});
