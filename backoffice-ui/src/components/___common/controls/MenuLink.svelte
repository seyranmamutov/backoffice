
<script>
  import Icon from '../../___common/Icon.svelte';
  export let href;
  export let title;
  export let iconName;
  export let active;
  let klass;
  export {klass as class};// = 'wh16';
  
  export let highlightPath = '';
  
  let classesAlways = "dif aic tdn-h fw6 ";
  let classesOnlyActive = "cw";
  let classesOnlyNotActive = "cli";
  
  $: aClasses = classesAlways +  (active?classesOnlyActive:classesOnlyNotActive); 
  
  
</script><a class="{aClasses}" href="{href}">
  <Icon class="{klass} {active? 'clb':'cb'}" n="{iconName}"></Icon>
  <div>{title}	</div></a>