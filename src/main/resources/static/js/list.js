(function() {
	$(function() {
		// 전체 스캔 버튼
		$('#all_scan').click(function() {
			var url = '/searchs/all';
			axios.post( 
				url
				, {
					cat: $(this).data('cat')
					, remote: $(this).data('remote')
				}
			).then(function(res) {
				console.log('test');
				alert('reload');
				document.location.reload();
			})
			;
		});
	});
})();
