package com.github.Minor2CCh.minium_me.compat;

import com.github.Minor2CCh.minium_me.block.TemporallyBlock;
import com.github.Minor2CCh.minium_me.block.entity.TemporallyBlockEntity;
import com.github.Minor2CCh.minium_me.compat.Jade.TemporallyBlockProvider;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class JadePlugin implements IWailaPlugin {
    @Override
    public void register(IWailaCommonRegistration registration) {
        //TODO register data providers
        registration.registerBlockDataProvider(TemporallyBlockProvider.INSTANCE, TemporallyBlockEntity.class);
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        //TODO register component providers, icon providers, callbacks, and config options here
        registration.registerBlockComponent(TemporallyBlockProvider.INSTANCE, TemporallyBlock.class);
    }
}
