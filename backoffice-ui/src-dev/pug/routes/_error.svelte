script.
	import Icon from '../components/___common/Icon.svelte';

	export let status;
	export let error;

	const dev = process.env.NODE_ENV === 'development';
svelte:head
	title {status}
	
|{#if status == 404}
div
	.w
		.tac.py48.py80-L
			Icon(class='wh104 cli mb16' n = 'm-emoticons')
			.cdi.fw7.lh13(style='font-size:123px;') 404   
			.fs16.fw6.mb32 Page not found   
|{:else if status == 500}
h1 {status}
p Server error
| {#if dev && error.stack}
p {error.message}
pre.
	\n{error.stack}
| {/if}
|{/if}				
