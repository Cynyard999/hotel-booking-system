<template>
    <div id="layout">
        <Header></Header>
        <transition name="fade-transform" mode="out-in">
            <router-view></router-view>
        </transition>
    </div>
</template>
<script>
import Header from '@/components/header'
export default {
    name: 'layout',
    components: {
        Header
    }
}
</script>
<style>
    #layout{
        padding: 20px 100px 144px;
        background: url('../assets/demon.png') repeat fixed top center;
    }

</style>