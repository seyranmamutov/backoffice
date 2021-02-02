script.
	import {stores} from '@sapper/app';
	import Loading from '../../components/___common/Loading.svelte';
	const {preloading} = stores();

	$: updatePreloaderPosition($preloading);

	function updatePreloaderPosition(preloading) {
		if (typeof window !== 'undefined' && preloading) {
			try {
				let w = document.getElementById('loadingDiv').offsetWidth;
				// let h = document.getElementById('loadingDiv').offsetHeight;
				jQuery('#loadingImg').offset({left: w/2});

			}catch (e) {
			}
		}
	}
	
slot
