package fj.instances;

import fj.F;
import fj.data.List;
import fj.data._FjData;
import fj.typeclasses.control.Monad;
import org.derive4j.hkt.__;

public final class List_ {
  private List_() {}

  public static Instance instances() { return Instance.self; }

  public enum  Instance implements Monad<List.µ> {
    self;

    @Override
    public <A> List<A> pure(A a) {
      return List.single(a);
    }

    @Override
    public <A, B> List<B> bind(__<List.µ, A> ma, F<A, __<List.µ, B>> f) {
      return _FjData.asList(ma).bind(a -> _FjData.asList(f.f(a)));
    }

    @Override
    public <A, B> List<B> apply(__<List.µ, F<A, B>> fab, __<List.µ, A> fa) {
      return _FjData.asList(fa).apply(_FjData.asList(fab));
    }

    @Override
    public <A, B> List<B> map(F<A, B> f, __<List.µ, A> fa) {
      return _FjData.asList(fa).map(f);
    }
  }
}
