script.
	import Icon from '../Icon.svelte';
	export let pageSize;
	export let totalPages;
	export let currentPage;
	
// PAGINATOR	
.bgci2.px12.py12.bt.btcb4
	.r.aic.jcsb
		.i0
			.df.aic.gx20
				.cb56.ml8 View
				select.select.h28.bdr3(bind:value=`{pageSize}`, on:change!=`{v =>  currentPage =1}`)
					option(value=5) 5
					option(value=10) 10
					option(value=15) 15
					option(value=50) 50
		.i0
			.df.aic.gx20
				.cb56 Page {currentPage} of {totalPages}
				button.button.wh40.brp.bgc9-hf.cli.ci-hf(on:click!=`{v => currentPage = currentPage - 1}` disabled!=`{currentPage <=1}`)
					Icon(class='wh14 ml-2' n='m-angle')
				button.button.wh40.brp.bgc9-hf.cli.ci-hf(on:click!=`{v => currentPage = currentPage + 1}` disabled!=`{currentPage >=totalPages}`)
					Icon(class='wh14 tsr180 mr-2' n='m-angle')
							
