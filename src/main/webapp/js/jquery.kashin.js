/**
 * 家臣団計算機スクリプト。
 * Original source : A-pZ
 * 2012/5/5
 *
 */

	//
	var lv = 10;
	var lv_max = 65;

	var status_min = 0;
	var status_max = 70;

	var base_status_min = 2;
	var base_status_max = 10;

	// ステータス
	var str = 0;
	var vit = 0;
	var agl = 0;
	var itl = 0;
	var cha = 0;
	var earth = 0;
	var water = 0;
	var fire = 0;
	var wind = 0;

	// 初期ステータス
	var i_str = 0;
	var i_vit = 0;
	var i_agl = 0;
	var i_itl = 0;
	var i_cha = 0;
	var i_earth = 0;
	var i_water = 0;
	var i_fire = 0;
	var i_wind = 0;

	// 選択した職
	var job = 0;

	// 職ラベル
	var job_label = ('侍','陰陽','忍者','僧','神職','鍛冶屋','薬師','傾奇者');

	// 初期振り
	var job_base = [
		[5,5,3,3,2,5,5,5,5],
		[2,2,4,6,4,7,7,7,7],
		[4,4,5,3,2,6,6,6,6],
		[4,4,2,4,4,6,7,6,6],
		[2,2,4,5,5,7,6,6,7],
		[4,4,5,2,3,5,5,5,5],
		[3,3,4,5,3,6,6,7,6],
		[4,2,5,4,3,6,6,6,6],
	];

	// 成長率
	// 生命、気合、腕力、耐久、器用、知力、魅力のそれぞれ10倍した値
	var job_addition = [
		[280,220,28,28,18,18,18] ,
		[220,280,18,18,20,28,26] ,
		[244,232,26,20,28,20,20] ,
		[268,256,22,24,18,24,18] ,
		[220,268,18,18,22,26,28] ,
		[256,220,24,26,24,18,22] ,
		[232,244,20,22,26,22,24] ,
		[232,244,22,18,28,24,22]
	];



	var hitpoint = function() {
		// 生命＝（基本成長率＋初期ステータス耐久値×１．２）×Ｌｖ
		return parseInt( ( job_addition[job][0] + i_vit * 12 ) *0.1 * lv );
	};

	var mentalpoint = function() {
		// 気合＝（基本成長率＋初期知力値×１．２）×Ｌｖ
		return parseInt( ( job_addition[job][1] + i_itl * 12 ) *0.1 * lv );
	};

	/**
	 * 職業を選択した後の初期振り値
	 */
	var jobselect = function() {
		i_str = job_base[job][0];
		i_vit = job_base[job][1];
		i_agl = job_base[job][2];
		i_itl = job_base[job][3];
		i_cha = job_base[job][4];
		i_earth = job_base[job][5];
		i_water = job_base[job][6];
		i_fire = job_base[job][7];
		i_wind = job_base[job][8];
	};

	/**
	 * 初期振り＋レベルによる基本ステータス計算
	 */
	var statuscalc = function() {
		// 初期ステータス値＋（基本成長率＋初期ステータス値×０．１）×（Ｌｖ－１）
		str = i_str + ( job_addition[job][2] + i_str) *0.1* ( lv - 1) ;
		vit = i_vit + ( job_addition[job][3] + i_vit) *0.1* ( lv - 1) ;
		agl = i_agl + ( job_addition[job][4] + i_agl) *0.1* ( lv - 1) ;
		itl = i_itl + ( job_addition[job][5] + i_itl) *0.1* ( lv - 1) ;
		cha = i_cha + ( job_addition[job][6] + i_cha) *0.1* ( lv - 1) ;
	};

	/**
	 * 属性ステータス計算
	 */
	var magicelement = function() {
		// 初期ステータス値＋（初期ステータス値÷２）×（Ｌｖ－１）
		earth = i_earth + ( i_earth / 2 ) * ( lv - 1);
		water = i_water + ( i_water / 2 ) * ( lv - 1);
		fire = i_fire + ( i_fire / 2 ) * ( lv - 1);
		wind = i_wind + ( i_wind / 2 ) * ( lv - 1);
	};