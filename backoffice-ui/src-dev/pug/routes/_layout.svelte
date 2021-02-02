script.
	import Icon from '../components/___common/Icon.svelte';
	import MenuLink from '../components/___common/controls/MenuLink.svelte';
	import {NetUtils} from "../service/NetUtils";
	import { goto, stores} from '@sapper/app';
	import {onMount} from 'svelte';
	import {buildUrl} from "../service/UrlUtils";

	const {session} = stores();

	export let segment;

svelte:head
	script(src = 'static/libs/jquery/jquery-ui.js')
	link( rel='stylesheet' href='static/libs/jquery/jquery-ui.css')

header.px20.py16.bgcdi.z5(style='box-shadow: 0px 1px 3px 0px rgba(0, 0, 0, 0.25);')
	.r.aic
		.i0
			a(href='')
				img(src="logo-192.png" width="32px" height="32px")
		.i.ord0.ps
			.db.db.collapse#burger
				.py0
					.df.fdc.fdr-X.gx24.gy20.fs12.ttu
						div
							MenuLink(title="Brands" href="brands" highlightPath="/brands" iconName="m-news" class="wh20 mr8" active=`{segment=='brands'}`)

main.df.fg1.h100vh.oh
	slot