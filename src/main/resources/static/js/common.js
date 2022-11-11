'use strict';

// 공통 global 변수
var $common = (function () {
    // 로깅 여부
    var _IS_LOG_ENABLE = 'prd' !== ENV;

    /**
     * axios 커스터마이징
     * @private
     */
    var _axiosInit = function () {
        // axios 기본 설정
        axios.defaults.baseURL = location.origin + '/mps/app-bff';
        axios.defaults.timeout = 5000;
        axios.defaults.activeRequestCount = 0; // 통신 중인 http request 갯수
        Object.freeze(axios.defaults);

        // axios request 인터셉터
        axios.interceptors.request.use(
            function (config) {
                config.activeRequestCount++; // 통신 요청에 따라 카운트 증가

                // TODO : 로딩바 노출

                return config;
            },
            function (error) {
                if (axios.defaults.activeRequestCount === 0) {
                // TODO : 로딩바 숨김
                }
                return Promise.reject(error);
            }
        );
        Object.freeze(axios.interceptors.request);

        // axios response 인터셉터
        axios.interceptors.response.use(
            function (response) {
                if (axios.defaults.activeRequestCount === 0) {
                // TODO : 로딩바 숨김
                }

                // request, response 로그 출력
                var config = response.config;
                var data = response.data;
                var url = config.url;
                var method = config.method;

                if (_IS_LOG_ENABLE) {
                    console.groupCollapsed(url);
                    console.log('request', {
                        method: method,
                        url: config.baseURL + url
                    });
                    console.log('response', data);
                    console.groupEnd();
                }

                return Promise.resolve(data);
            },
            function (error) {
                console.log(error);

                if (axios.defaults.activeRequestCount === 0) {
                // TODO : 로딩바 숨김
                }

                $common.alert('현재 서비스 접속이 원활하지 않습니다.', '에러');

                return Promise.reject(error);
            }
        );
        Object.freeze(axios.interceptors.response);
    };
    _axiosInit();

    /**
     * 공통 (Alert, Confirm, Layer Popup) Dialog 열기
     *
     * @private
     * @param {text} text 공통 Dialog 문구 HTML
     * @param {string} title 타이틀
     * @param {Object} options 공통 Dialog 옵션
     * @param {string} [options.addClass] 팝업 디자인이 달라질 경우 커스터마이징을 위한 클래스
     * @param {string} [options.confirmButton] 확인 버튼 문구
     * @param {string} [options.cancelButton] 취소 버튼 문구
     * @param {number} [options.width] 레이어 가로 사이즈
     * @param {Function} [options.confirm] 확인 callback 함수
     * @param {Function} [options.cancel] 취소 callback 함수
     * @param {Function} [options.changeBR] \n => <br> 변경
     */
    var _openCommonDialog = function (text, title, options) {
        var defaultOptions = {
            addClass: null, // 팝업 디자인이 달라질 경우 커스터마이징을 위한 클래스
            confirmButton: '확인', // 기본 확인버튼 레이블은 '확인' 레이블 수정시에는 옵션 추가하여 텍스트 변경
            cancelButton: null, // 기본 취소버튼은 null 이므로 버튼 추가시엔 옵션 추가하여 변경
            width: null, // JS로 사이즈 지정 가능 옵션 (버튼을 제외한 컨텐츠 영역의 사이즈이기에 외곽 DIV에 패딩값등을 포함하지않음)
            confirm: null, // 확인버튼 callback method
            cancel: null, // 취소버튼 callback method
            changeBR: false // \n => <br> 변경
        };
        var mergedOptions = $.extend(true, defaultOptions, options);
        var dialogStr = '';
        if (title) {
            dialogStr += '<h2>' + title + '</h2>';
        }
        dialogStr += '<p class="cts-wrap">' + text + '</p>';

        utils.pop.alert(dialogStr, mergedOptions);
    };

    // $common.xxxx 형태로 호출할 함수 정의 e.g.) $common.alert()
    return {
        /**
         * Alert Dialog
         *
         * @param {string} text alert 문구 HTML
         * @param {string} title 타이틀
         * @param {string} [options.title] 타이틀
         * @param {string} [options.confirmButton] 확인 버튼 문구
         * @param {Function} [options.confirm] 확인 callback 함수
         * @param {string} [options.addClass] 팝업 디자인이 달라질 경우 커스터마이징을 위한 클래스
         * @param {number} [options.width] 레이어 가로 사이즈
         * @param {Boolean} [options.changeBR] \n => <br> 변경
         */
        alert: function (text, title, options) {
            var alertOptions = options || {};
            alertOptions.cancelButton = null;
            alertOptions.cancel = null;
            _openCommonDialog(text, title, alertOptions);
        },
        /**
         * Confirm Dialog
         *
         * @param {string} text confirm 문구 HTML
         * @param {string} title 타이틀
         * @param {Object} options confirm 옵션
         * @param {string} options.confirmButton 확인 버튼 문구
         * @param {Function} options.confirm 확인 callback 함수
         * @param {string} [options.cancelButton] 취소 버튼 문구
         * @param {Function} [options.cancel] 취소 callback 함수
         * @param {string} [options.addClass] 팝업 디자인이 달라질 경우 커스터마이징을 위한 클래스
         * @param {number} [options.width] 레이어 가로 사이즈
         * @param {Boolean} [options.changeBR] \n => <br> 변경
         */
        confirm: function (text, title, options) {
            var confirmOptions = options || {};
            confirmOptions.cancelButton = confirmOptions.cancelButton || '취소';
            _openCommonDialog(text, title, confirmOptions);
        },
        /**
         * Layer Popup Dialog
         *
         * @param {string} selector Layer Popup 최상의 태그의 CSS 셀렉터 e.g.) #layerPopUp
         * @param {string} title 타이틀
         * @param {Object} [options] Layer Popup 옵션
         * @param {string} options.confirmButton 확인 버튼 문구
         * @param {Function} options.confirm 확인 callback 함수
         * @param {string} [options.cancelButton] 취소 버튼 문구
         * @param {Function} [options.cancel] 취소 callback 함수
         * @param {string} [options.addClass] 팝업 디자인이 달라질 경우 커스터마이징을 위한 클래스
         * @param {number} [options.width] 레이어 가로 사이즈
         * @param {Boolean} [options.changeBR] \n => <br> 변경
         */
        layerPopup: function (selector, title, options) {
            var layerOptions = options || {};
            layerOptions.addClass = layerOptions.addClass || 'layerStyle'; // 레이어팝업용 클래스 추가해서 스타일링

            var el = $(selector);
            el.remove();
            _openCommonDialog(el.html(), title, layerOptions);
        },
        /**
         * Full Popup
         *
         * @param {string} selector Full Popup 최상의 태그의 CSS 셀렉터 e.g.) #fullPopUp
         */
        fullPopup: function (selector) {
            utils.pop.open(selector);
        },
        /**
         * Toast Popup
         *
         * @param {string} text Toast Popup 문구
         */
        toastPopup: function (text) {
            $('#toastContainer').text(text);
            utils.ToastPop.open('toastContainer');
        },
        /**
         * 앱 여부
         *
         */
        isApp: function () {
            return ['AOS', 'IOS'].includes(commAppChkOSType());
        },
        /**
         * 모바일 여부
         *
         */
        isMobile: function () {
            return 'win16|win32|win64|macintel|mac'.indexOf(navigator.platform.toLowerCase()) < 0;
        },
        /**
         * 무한스크롤 (더보기) 이벤트 등록
         *
         * @param {Function} callback 화면 하단 도달 시 실행할 function
         * @return {Function} 이벤트 등록한 function (removeEventListener 필요 시 사용)
         */
        addScrollEvent: function (callback) {
            var scrollEvent = function () {
                (utils.etc._debounce(function () {
                    var scrollTop = utils.etc._scrollTop();
                    var bottom = document.body.clientHeight - window.innerHeight;
                    if (scrollTop >= bottom - 30) {
                        callback();
                    }
                }, 10))();
            };

            // scroll event debounce 적용
            window.addEventListener('scroll', scrollEvent); // debounce timer set 300

            return scrollEvent;
        },
        /**
         * 무한스크롤 (더보기) 이벤트 제거
         *
         * @param {Function} scrollEvent 이벤트 제거할 function
         */
        removeScrollEvent: function (scrollEvent) {
            window.removeEventListener('scroll', scrollEvent);
        },
        /**
         * 메인으로 이동
         *
         */
        gotoMain: function () {
            // TODO
            alert('메인 페이지로 이동 (작업중)');
        },
        /**
         * 로그인 페이지로 이동
         *
         */
        gotoLogin: function () {
            // TODO
            alert('로그인 페이지로 이동 (작업중)');
        }
    };
})();

// DOM Tree 생성 완료 후 실행
$(function () {
    // 서브 헤더 백 버튼
    $('#subHeaderBackBtn').click(function (e) {
        e.preventDefault();

        history.back();
    });
});
