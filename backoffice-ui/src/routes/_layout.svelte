
<script>
  import Icon from '../components/___common/Icon.svelte';
  import MenuLink from '../components/___common/controls/MenuLink.svelte';
  import {NetUtils} from "../service/NetUtils";
  import { goto, stores} from '@sapper/app';
  import {onMount} from 'svelte';
  import {buildUrl} from "../service/UrlUtils";
  
  const {session} = stores();
  
  export let segment;
  
</script>
<svelte:head>
  <script src="static/libs/jquery/jquery-ui.js"></script>
  <link rel="stylesheet" href="static/libs/jquery/jquery-ui.css">
</svelte:head>
<header class="px20 py16 bgcdi z5" style="box-shadow: 0px 1px 3px 0px rgba(0, 0, 0, 0.25);">
  <div class="r aic">
    <div class="i0"><a href=""><img src="logo-192.png" width="32px" height="32px"></a></div>
    <div class="i ord0 ps">
      <div class="db db collapse" id="burger">
        <div class="py0">
          <div class="df fdc fdr-X gx24 gy20 fs12 ttu">
            <div>
              <MenuLink class="wh20 mr8" title="Brands" href="brands" highlightPath="/brands" iconName="m-news" active="{segment=='brands'}"></MenuLink>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</header>
<main class="df fg1 h100vh oh">
  <slot></slot>
</main>