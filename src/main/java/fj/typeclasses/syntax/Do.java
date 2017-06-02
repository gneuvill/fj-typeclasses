package fj.typeclasses.syntax;

import fj.F;
import fj.F0;
import fj.F2;
import fj.P2;
import fj.typeclasses.control.Bind;
import org.derive4j.hkt.TypeEq;
import org.derive4j.hkt.__;

import static fj.P.p;

public final class Do {
  private Do() {}

  // ############### F<A, B>

  public static <A, B, m> __<m, B> $(Bind<m> M
    , F0<__<m, A>> ma
    , F<A, __<m, B>> f) {
    return M.bind(ma.f(), f);
  }

  public static <A, B, m, R> R $(Bind<m> M, TypeEq<__<m, B>, R> eq
    , F0<__<m, A>> ma
    , F<A, __<m, B>> f) {
    return eq.coerce($(M, ma, f));
  }

  // ############### F2<A, B, C>

  public static <A, B, C, m> __<m, C> $(Bind<m> M
    , F0<__<m, A>> ma
    , F<A, __<m, B>> fmb
    , F2<A, B, __<m, C>> f) {
    return M.bind(ma.f(), a -> M.bind(fmb.f(a), b -> f.f(a, b)));
  }

  public static <A, B, C, m> __<m, C> $(Bind<m> M
    , F0<__<m, A>> ma
    , F<A, __<m, B>> fmb
    , F<P2<A, B>, __<m, C>> f) {
    return $(M, ma, fmb, (a, b) -> f.f(p(a, b)));
  }

  public static <A, B, C, m, R> R $(Bind<m> M, TypeEq<__<m, C>, R> eq
    , F0<__<m, A>> ma
    , F<A, __<m, B>> fmb
    , F2<A, B, __<m, C>> f) {
    return eq.coerce($(M, ma, fmb, f));
  }

  public static <A, B, C, m, R> R $(Bind<m> M, TypeEq<__<m, C>, R> eq
    , F0<__<m, A>> ma
    , F<A, __<m, B>> fmb
    , F<P2<A, B>, __<m, C>> f) {
    return eq.coerce($(M, ma, fmb, f));
  }

  // ############### F3<A, B, C, D>

//    public static <A, B, C, D, m, MD> MD $(Monad<m> M, Coerce<m, D, MD> N,
//                                           F0<__<m, A>> ma,
//                                           F<A, __<m, B>> fmb,
//                                           F2<A, B, __<m, C>> fmc,
//                                           F3<A, B, C, D> f) {
//        return N.coerce(M.bind(ma.f(), a -> M.bind(fmb.f(a), b -> M.fmap(fmc.f(a, b), c -> f.f(a, b, c)))));
//    }
//
//    public static <A, B, C, D, m, MD> MD $(Monad<m> M, Coerce<m, D, MD> N,
//                                           F0<__<m, A>> ma,
//                                           F<A, __<m, B>> fmb,
//                                           F<P2<A, B>, __<m, C>> fmc,
//                                           F3<A, B, C, D> f3) {
//        return $(M, N, ma, fmb, (a, b) -> fmc.f(p(a, b)), f3);
//    }
//
//    public static <A, B, C, D, m, MD> MD $(Monad<m> M, Coerce<m, D, MD> N,
//                                           F0<__<m, A>> ma,
//                                           F<A, __<m, B>> fmb,
//                                           F2<A, B, __<m, C>> fmc,
//                                           F<P3<A, B, C>, D> f) {
//        return $(M, N, ma, fmb, fmc, (a, b, c) -> f.f(p(a, b, c)));
//    }
//
//    public static <A, B, C, D, m, MD> MD $(Monad<m> M, Coerce<m, D, MD> N,
//                                           F0<__<m, A>> ma,
//                                           F<A, __<m, B>> fmb,
//                                           F<P2<A, B>, __<m, C>> fmc,
//                                           F<P3<A, B, C>, D> f) {
//        return $(M, N, ma, fmb, fmc, (a, b, c) -> f.f(p(a, b, c)));
//    }
//
//    // ############### F4<A, B, C, D, E>
//
//    public static <A, B, C, D, E, m, ME> ME $(Monad<m> M, Coerce<m, E, ME> N,
//                                              F0<__<m, A>> ma,
//                                              F<A, __<m, B>> fmb,
//                                              F2<A, B, __<m, C>> fmc,
//                                              F3<A, B, C, __<m, D>> fmd,
//                                              F4<A, B, C, D, E> f) {
//        return N.coerce(M.bind(ma.f(), a -> M.bind(fmb.f(a), b -> M.bind(fmc.f(a, b), c ->
//            M.fmap(fmd.f(a, b, c), d -> f.f(a, b, c, d))))));
//    }
//
//    public static <A, B, C, D, E, m, ME> ME $(Monad<m> M, Coerce<m, E, ME> N,
//                                              F0<__<m, A>> ma,
//                                              F<A, __<m, B>> fmb,
//                                              F<P2<A, B>, __<m, C>> fmc,
//                                              F3<A, B, C, __<m, D>> fmd,
//                                              F4<A, B, C, D, E> f) {
//        return $(M, N, ma, fmb, (a, b) -> fmc.f(p(a, b)), fmd, f);
//    }
//
//    public static <A, B, C, D, E, m, ME> ME $(Monad<m> M, Coerce<m, E, ME> N,
//                                              F0<__<m, A>> ma,
//                                              F<A, __<m, B>> fmb,
//                                              F2<A, B, __<m, C>> fmc,
//                                              F<P3<A, B, C>, __<m, D>> fmd,
//                                              F4<A, B, C, D, E> f) {
//        return $(M, N, ma, fmb, fmc, (a, b, c) -> fmd.f(p(a, b, c)), f);
//    }
//
//
//    public static <A, B, C, D, E, m, ME> ME $(Monad<m> M, Coerce<m, E, ME> N,
//                                              F0<__<m, A>> ma,
//                                              F<A, __<m, B>> fmb,
//                                              F2<A, B, __<m, C>> fmc,
//                                              F3<A, B, C, __<m, D>> fmd,
//                                              F<P4<A, B, C, D>, E> f) {
//        return $(M, N, ma, fmb, fmc, fmd, (a, b, c, d) -> f.f(p(a, b, c, d)));
//    }
//
//    public static <A, B, C, D, E, m, ME> ME $(Monad<m> M, Coerce<m, E, ME> N,
//                                              F0<__<m, A>> ma,
//                                              F<A, __<m, B>> fmb,
//                                              F<P2<A, B>, __<m, C>> fmc,
//                                              F<P3<A, B, C>, __<m, D>> fmd,
//                                              F4<A, B, C, D, E> f) {
//        return $(M, N, ma, fmb, (a, b) -> fmc.f(p(a, b)), fmd, f);
//    }
//
//    public static <A, B, C, D, E, m, ME> ME $(Monad<m> M, Coerce<m, E, ME> N,
//                                              F0<__<m, A>> ma,
//                                              F<A, __<m, B>> fmb,
//                                              F<P2<A, B>, __<m, C>> fmc,
//                                              F3<A, B, C, __<m, D>> fmd,
//                                              F<P4<A, B, C, D>, E> f) {
//        return $(M, N, ma, fmb, (a, b) -> fmc.f(p(a, b)), fmd, f);
//    }
//
//    public static <A, B, C, D, E, m, ME> ME $(Monad<m> M, Coerce<m, E, ME> N,
//                                              F0<__<m, A>> ma,
//                                              F<A, __<m, B>> fmb,
//                                              F2<A, B, __<m, C>> fmc,
//                                              F<P3<A, B, C>, __<m, D>> fmd,
//                                              F<P4<A, B, C, D>, E> f) {
//        return $(M, N, ma, fmb, fmc, fmd, (a, b, c, d) -> f.f(p(a, b, c, d)));
//    }
//
//    public static <A, B, C, D, E, m, ME> ME $(Monad<m> M, Coerce<m, E, ME> N,
//                                              F0<__<m, A>> ma,
//                                              F<A, __<m, B>> fmb,
//                                              F<P2<A, B>, __<m, C>> fmc,
//                                              F<P3<A, B, C>, __<m, D>> fmd,
//                                              F<P4<A, B, C, D>, E> f) {
//        return $(M, N, ma, fmb, fmc, fmd, (a, b, c, d) -> f.f(p(a, b, c, d)));
//    }

}
