package cz.upce.fei.boop.pujcovna.generator;

import cz.upce.fei.boop.pujcovna.data.Nastroj;
import cz.upce.fei.boop.pujcovna.generator.generators.BiciNastrojGenerator;
import cz.upce.fei.boop.pujcovna.generator.generators.KlavesovyNastrojGenerator;
import cz.upce.fei.boop.pujcovna.generator.generators.NastrojGenerator;
import cz.upce.fei.boop.pujcovna.generator.generators.SmyccovyNastrojGenerator;

public class Generator {
    final private NastrojGenerator biciNastrojGenerator = new BiciNastrojGenerator();
    final private NastrojGenerator klavesovyNastrojGenerator = new KlavesovyNastrojGenerator();
    final private NastrojGenerator smyccovyNastrojGenerator = new SmyccovyNastrojGenerator();

    public Nastroj generujBiciNastroj() {
        return biciNastrojGenerator.generujNastroj();
    }

    public Nastroj generujKlavesovyNastroj() {
        return klavesovyNastrojGenerator.generujNastroj();
    }

    public Nastroj generujSmyccovyNastroj() {
        return smyccovyNastrojGenerator.generujNastroj();
    }
}

