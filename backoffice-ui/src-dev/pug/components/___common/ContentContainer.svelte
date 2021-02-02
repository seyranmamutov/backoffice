script.
	let klass ;
	export {klass as class}
	export let bgImage = false; 

| {#if bgImage}
.p20.p24-L.w100p.oya.bgsc.bgrnr(class=`{klass}` style='background-image: url(static/img/backgrounds/corner.png); background-position: right top;')
	.pr
		slot
|{:else}
.p20.p24-L.w100p.bl.blci6.oya(class=`{klass}`)
	.pr
		slot
| {/if}