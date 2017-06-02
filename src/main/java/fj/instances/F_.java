package fj.instances;

import fj.*;
import fj.data.Either;
import fj.typeclasses.control.Category;
import fj.typeclasses.control.Monad;
import fj.typeclasses.data.profunctor.Choice;
import fj.typeclasses.data.profunctor.Strong;
import org.derive4j.hkt.__;

public final class F_ {
  private F_() {}

  @SuppressWarnings("unchecked")
  public static <A> Instances<A> instances() { return (Instances<A>) Instances.self; }

  public static final class Instances<X> implements
    Monad<__<F.µ, X>>
    , Category<F.µ>
    , Strong<F.µ>
    , Choice<F.µ> {

    private Instances() {}
    private static final Instances<?> self = new Instances<>();

    @Override
    public <A, B> F<X, B> map(F<A, B> f, __<__<F.µ, X>, A> fa) {
      return compose(f, _Fj.asF(fa));
    }

    @Override
    public <A, B> F<X, B> apply(__<__<F.µ, X>, F<A, B>> fab, __<__<F.µ, X>, A> fa) {
      return x -> _Fj.asF(fab).f(x).f(_Fj.asF(fa).f(x));
    }

    @Override
    public <A, B> F<X, B> bind(__<__<F.µ, X>, A> ma, F<A, __<__<F.µ, X>, B>> f) {
      return x -> _Fj.asF(f.f(_Fj.asF(ma).f(x))).f(x);
    }

    @Override
    public <A> F<X, A> pure(A a) {
      return Function.constant(a);
    }

    @Override
    public <B, C, D> F<B, D> compose(__<__<F.µ, C>, D> f, __<__<F.µ, B>, C> g) {
      return Function.compose(_Fj.asF(f), _Fj.asF(g));
    }

    @Override
    public <T> F<T, T> id() { return Function.identity(); }

    @Override
    public <A, B, C, D> F<A, D> dimap(F<A, B> a2b, F<C, D> c2d, __<__<F.µ, B>, C> b2c) {
      return F1W.lift(c2d).o(_Fj.asF(b2c)).o(a2b);
    }

    @Override
    public <A, B, C> F<P2<A, C>, P2<B, C>> first(__<__<F.µ, A>, B> pab) {
      return ac -> P.p(_Fj.asF(pab).f(ac._1()), ac._2());
    }

    @Override
    public <A, B, C> F<P2<A, B>, P2<A, C>> second(__<__<F.µ, B>, C> pbc) {
      return ab -> P2_.<A>instances().map(_Fj.asF(pbc), ab);
    }

    @Override
    public <A, B, C> F<Either<A, C>, Either<B, C>> left(__<__<F.µ, A>, B> pab) {
      return ei -> ei.either(a -> Either.left(_Fj.asF(pab).f(a)), Either::right);
    }

    @Override
    public <A, B, C> F<Either<A, B>, Either<A, C>> right(__<__<F.µ, B>, C> pab) {
      return ei -> Either_.<A>instances().map(_Fj.asF(pab), ei);
    }
  }
}
