package fj.instances;

import fj.F;
import fj.P2;
import fj._Fj;
import fj.typeclasses.data.Functor;
import org.derive4j.hkt.__;

public final class P2_ {
  private P2_() {}

  @SuppressWarnings("unchecked")
  public static <A> Instances<A> instances() { return (Instances<A>) Instances.self; }

  public static final class Instances<X> implements Functor<__<P2.µ, X>> {
    private static Instances<?> self = new Instances<>();
    private Instances() {}

    @Override
    public <A, B> P2<X, B> map(F<A, B> f, __<__<P2.µ, X>, A> fa) {
      return _Fj.asP2(fa).map2(f);
    }
  }
}
