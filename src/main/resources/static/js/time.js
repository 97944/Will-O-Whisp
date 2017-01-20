function getRelativeTime(baseDateStr, targetDateStr){

    var baseDate = new Date(baseDateStr);
    var targetDate = new Date(targetDateStr);

    var elapsedTime = Math.ceil((baseDate.getTime() - targetDate.getTime())/1000);

    var message = null;

    // これ以下で一定時間未満のごとのメッセージの表示方法を条件分岐
    // 表示する数字が1桁のときは関数 digit で頭に半角スペースを付ける
    if (elapsedTime < 60) { //  1 分未満
        message =  'たった今';
    } else if (elapsedTime < 120) { //  2 分未満
        message =  '約 1分前';
    } else if (elapsedTime < (60*60)) { //  1 時間未満
        message =  '約' + (Math.floor(elapsedTime / 60) < 10 ? ' ' : '') + Math.floor(elapsedTime / 60) + '分前';
    } else if (elapsedTime < (120*60)) { //  2 時間未満
        message =  '約 1時間前';
    } else if (elapsedTime < (24*60*60)) { //  1 日未満
        message =  '約' + (Math.floor(elapsedTime / 3600) < 10 ? ' ' : '') + Math.floor(elapsedTime / 3600) + '時間前';
    } else if (elapsedTime < (7*24*60*60)) { // 1 週間未満
        message =  '約' + (Math.floor(elapsedTime / 86400) < 10 ? ' ' : '') +Math.floor(elapsedTime / 86400) + '日前';
    } else { // 1 週間以上
        message =  '約' + (Math.floor(elapsedTime / 604800) < 10 ? ' ' : '') + Math.floor(elapsedTime / 604800) + '週間前';
    }

    return message;
}
