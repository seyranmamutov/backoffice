script.
	export let actionInProgress = true;
	
|{#if actionInProgress}
.pa.l0.t0.r0.b0.df.aic.jcc.br5.z5(style='background-color: rgba(255, 255, 255, 0.87)')
	img(src="static/img/blue-loading.gif", alt="")
|{/if}