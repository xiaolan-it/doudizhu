var cardArr1 = "3♥,4♥,5♠,6♥,7♠,8♣,9♥,10♠,10♥,10♦,11♥,13♠,13♦,14♥,14♣,14♠,15♦,17".split(",");
var cardArr2 = "3♠,4♦,5♥,5♣,6♣,7♥,8♥,9♠,9♣,10♣,11♠,11♣,11♦,12♠,13♣,13♥,15♣,15♠".split(",");
var cardArr3 = "3♣,3♦,4♣,4♠,5♦,6♠,6♦,7♦,7♣,8♠,8♦,9♦,12♣,12♦,12♥,14♦,15♥,16".split(",");
/**
	 * ##############斗地主规则：#######
		一、牌面比较
		1>  1、2、3张牌 ：
				·检测是否是同一张牌
				·在比value大小
				·2张可能是大小王炸弹
		2>	4张牌（3带1）
				·检测是否3带1
				·拿到3个中的一个值去比大小
				·或炸弹
		3>	5+张牌
				1、顺子
					拿最大（小）值比大小。
				2、非顺子：
					4的倍数张牌
						1、连续的3带1
					8张 4带2对
		4>  不管出什么牌，只要对方选择2张 或4张 就检测是否是炸弹，在比大小
		5> 牌打完了就赢了 ，初始赔率倍数，一个炸弹就+倍数*1

	 */

function getCardVal(v) {
    switch (v) {
    case 11:
        v = "J";
        break;
    case 12:
        v = "Q";
        break;
    case 13:
        v = "K";
        break;
    case 14:
        v = "A";
        break;
    case 15:
        v = "2";
        break;
    case 16:
        v = "J<br>Q<br>K<br>E<br>R";
        break;
    case 17:
        v = "J<br>Q<br>K<br>E<br>R";
        break;
    default:
        v = v;

    }
    return v;
}

//检测大小王
function isMaxCard(v) {
    // ♠♣♦♥
    if ( - 1 != v.indexOf("♠")) return false;
    else if ( - 1 != v.indexOf("♥")) return false;
    else if ( - 1 != v.indexOf("♣")) return false;
    else if ( - 1 != v.indexOf("♦")) return false;
    return true;
}

//发牌
function fapai(cardArr,index) {
    //初始化牌
    var cardHtml = "";
    var cardObj = new Array(); //牌值及花色
    var huaseClass; //默认黑色样式
    var cardObjLength = 0;
    var c123 =  (1!=index?(2==index?"card_div2":"card_div3"):"");
    console.log(c123);
    for (var i = 0; i < cardArr.length; i++) {
        huaseClass = "black"; //默认黑色样式
        cardObjLength = cardArr[i].length;

        if (isMaxCard(cardArr[i])) { // 大小王
            cardObj[0] = cardArr[i];
            cardObj[1] = "";
        } else {
            cardObj[0] = cardArr[i].substring(0, cardObjLength - 1);
            cardObj[1] = cardArr[i].substring(cardObjLength - 1, cardObjLength);
        }
        if (parseInt(cardObj[0]) < 16 && -1 != "♦♥".indexOf(cardObj[1])) { //红色
            huaseClass = "red";
        } else if (16 == parseInt(cardObj[0])) {
            huaseClass = "black";
        } else if (17 == parseInt(cardObj[0])) {
            huaseClass = "red";
        }
      
        cardHtml += '<div class="card_div ' + huaseClass +' '+c123+'">'; //  (1!=index?(2==index?"card_div2":"card_div3"):"")
        cardHtml += '<p>' + getCardVal(parseInt(cardObj[0])) + '</p>';
        cardHtml += '<p>' + cardObj[1] + '</p></div>';
    }

    $("#card_container"+index).html(cardHtml);
}
function play() {
    $("#out_container").append($(".card_container").find(".card_top"));
    $(".card_container .card_top").remove();
}

$(function() {

    $("#cardArr").html(cardArr1 + "\n"+cardArr2+"\n"+cardArr3);

    fapai(cardArr1,1);
    fapai(cardArr2,2);
    fapai(cardArr3,3);

    $(".card_div").click(function(t) {

        if ("y" == $(this).attr("data-select")) { //收牌
            $(this).removeAttr("data-select");
            $(this).removeClass("card_top");
            isZheDie = false;
        } else {
            $(this).attr("data-select", 'y'); //标记已展开
            $(this).addClass("card_top");
            isZheDie = true;
        }
    });

    var isZheDie = false; //是否收牌（折叠）
    //鼠标是否一直在按着
    var isMouseDowning = false;
    $(".card_div").mousedown(function() {
        isMouseDowning = true;
        if ("y" == $(this).attr("data-select")) { //收牌
            isZheDie = true;
        } else {
            isZheDie = false; //展开牌
        }
    });

    $(document).mouseup(function() { //鼠标弹起来
        isMouseDowning = false;
    });

    //多选牌
    $(".card_div").mousemove(function() {
        if (isMouseDowning) {

            if (!isZheDie) { //展开牌
                $(this).attr("data-select", 'y'); //标记已展开
                $(this).addClass("card_top");
            } else { //收牌
                $(this).removeAttr("data-select");
                $(this).removeClass("card_top");
            }
        }
    });
});